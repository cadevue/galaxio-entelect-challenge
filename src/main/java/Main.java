import Enums.*;
import Models.*;
import Services.*;
import com.microsoft.signalr.*;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BotService botService = new BotService();
        String token = System.getenv("Token");
        token = (token != null) ? token : UUID.randomUUID().toString();

        String environmentIp = System.getenv("RUNNER_IPV4");

        String ip = (environmentIp != null && !environmentIp.isBlank()) ? environmentIp : "localhost";
        ip = ip.startsWith("http://") ? ip : "http://" + ip;

        String url = ip + ":" + "5000" + "/runnerhub";

        HubConnection hubConnection = HubConnectionBuilder.create(url)
                .build();

        hubConnection.on("Disconnect", (id) -> {
            // On disconnect with the runner
            System.out.println("Disconnected:");

            hubConnection.stop();
        }, UUID.class);

        hubConnection.on("Registered", (id) -> {
            // On registered with the runner
            System.out.println("Registered with the runner " + id);

            Position position = new Position();
            GameObject bot = new GameObject(id, 10, 20, 0, position, ObjectTypes.Player, 0, 0, 0, 0, 0);
            botService.setBot(bot);
        }, UUID.class);

        hubConnection.on("ReceiveGameState", (gameStateDto) -> {
            // Receive game state from the runner
            GameState gameState = new GameState();
            gameState.world = gameStateDto.getWorld();

            for (Map.Entry<String, List<Integer>> objectEntry : gameStateDto.getGameObjects().entrySet()) {
                gameState.getGameObjects().add(GameObject.FromStateList(UUID.fromString(objectEntry.getKey()), objectEntry.getValue()));
            }

            for (Map.Entry<String, List<Integer>> objectEntry : gameStateDto.getPlayerObjects().entrySet()) {
                gameState.getPlayerGameObjects().add(GameObject.FromStateList(UUID.fromString(objectEntry.getKey()), objectEntry.getValue()));
            }

            botService.setGameState(gameState);
        }, GameStateDto.class);

        hubConnection.on("ReceivePlayerConsumed", () -> {
            System.out.println("Player consumed");
            hubConnection.stop();
        });

        hubConnection.on("ReceiveGameComplete", (complete) -> {
            System.out.println("Game complete");
            hubConnection.stop();
        }, GameCompleteDto.class);

        hubConnection.start().blockingAwait();

        Thread.sleep(1000);
        System.out.println("Registering with the runner...");
        hubConnection.send("Register", token, "The Cadevue");

        //This is a blocking call
        hubConnection.start().subscribe(() -> {
            while (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                Thread.sleep(20);

                GameObject bot = botService.getBot();
                if (bot == null) {
                    continue;
                }

                botService.getPlayerAction().setPlayerId(bot.getId());
                botService.computeNextPlayerAction(botService.getPlayerAction());
                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                    hubConnection.send("SendPlayerAction", botService.getPlayerAction());
                }
            }
        });

        hubConnection.stop();
        System.exit(0);
    }
}
