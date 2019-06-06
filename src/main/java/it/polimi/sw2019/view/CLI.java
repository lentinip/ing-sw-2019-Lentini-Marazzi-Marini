package it.polimi.sw2019.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;


import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CLI implements ViewInterface {

    /**
     * Customized constructor
     */
    public CLI(Client client){

        setClient(client);
        Gson json = new Gson();
        InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("/AmmoTiles/AmmoTileDescription.json");
        Type map = new TypeToken<HashMap<String, String>>(){}.getType();
        try {
            ammoTileDescription = json.fromJson(jsonFile.toString(), map);
        }
        catch (Exception e){
            out.println("file not found");
        }

    }

    /**
     * Default constructor for test
     */
    public CLI() {

        setClient(client);
        Gson json = new Gson();
        InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("/AmmoTiles/AmmoTileDescription.json");
        Type map = new TypeToken<HashMap<String, String>>(){}.getType();
        try {
            ammoTileDescription = json.fromJson(jsonFile.toString(), map);
        }
        catch (Exception e){
            out.println("file not found");
        }
    }

    /* Attributes */

    private MatchState matchState; //the current matchState

    private PrivateHand privateHand; //the current privateHand

    private String username; //player user

    private List<String> usernames = new ArrayList<>(); //username of all players

    private List<Character> characters = new ArrayList<>();

    private static final Map<Integer, String> boards;

    static {

        Map<Integer, String> boardMap = new HashMap<>();
        boardMap.put(1, "Board4.json"); //small
        boardMap.put(2, "Board3.json"); //medium
        boardMap.put(3, "Board1.json"); //medium 2
        boardMap.put(4, "Board2.json"); //large

        boards = Collections.unmodifiableMap(boardMap);
    }

    private static final Map<String, String> boardSize;

    static {

        Map<String, String> maps = new HashMap<>();
        maps.put("Board4.json", "SMALL");
        maps.put("Board3.json", "MEDIUM");
        maps.put("Board1.json", "MEDIUM");
        maps.put("Board2.json", "LARGE");

        boardSize = Collections.unmodifiableMap(maps);
    }

    private  Map<String, String> ammoTileDescription = new HashMap<>();

    private Client client;

    /* COLORS !!! */
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @SuppressWarnings("squid:S106")
    private static PrintWriter out = new PrintWriter(System.out, true);

    private static Scanner in = new Scanner(System.in);

    private static final Logger LOGGER = Logger.getLogger("cli");



    /* Methods */

    public static PrintWriter getOut() {
        return out;
    }

    public Map<String, String> getAmmoTileDescription() {
        return ammoTileDescription;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }

    public PrivateHand getPrivateHand() {
        return privateHand;
    }

    public void setPrivateHand(PrivateHand privateHand) {
        this.privateHand = privateHand;
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public static void main(String[] args){

        CLI prova = null;


        prova = new CLI();




        List<String> usernames = new ArrayList<>();
        usernames.add("prova1");
        usernames.add("prova2");
        usernames.add("prova3");
        usernames.add("prova4");
        usernames.add("prova5");
        prova.setUsernames(usernames);
        prova.setUsername("prova1");

        //TESTING DISPLAY OPTIONS
        MatchState matchState = new MatchState();
        List<MessageCell> cells = new ArrayList<>();
        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        characters.add(Character.SPROG);
        prova.setCharacters(characters);

        MessageCell cell0 = new MessageCell(0, 0, characters, true, null, null);
        List<String> weapons = new ArrayList<>();
        weapons.add("CYBER BLADE");
        weapons.add("SHOTGUN");
        List<String> weaponsEmpty = new ArrayList<>();
        List<Character> characters1 = new ArrayList<>();

        MessageCell cell1 = new MessageCell(0, 1, characters1, false, null, weapons);
        MessageCell cell2 = new MessageCell(0, 2, characters1, false, "AD_ammo_042.png", null);

        cells.add(cell0);
        cells.add(cell1);
        cells.add(cell2);
        matchState.setCells(cells);

        List<PlayerBoardMessage> playerBoards = new ArrayList<>();
        PlayerBoardMessage playerBoardMessage = new PlayerBoardMessage();
        playerBoardMessage.setYellowAmmo(1);
        playerBoardMessage.setBlueAmmo(0);
        playerBoardMessage.setRedAmmo(2);
        playerBoardMessage.setNumOfDeaths(1);
        playerBoardMessage.setFlipped(false);
        playerBoardMessage.setFirstPlayer(true);
        playerBoardMessage.setDamageSequence(characters);
        playerBoardMessage.setMarkSequence(characters1);
        playerBoards.add(playerBoardMessage);
        PlayerBoardMessage playerBoardMessage2 = new PlayerBoardMessage();
        playerBoardMessage2.setYellowAmmo(1);
        playerBoardMessage2.setBlueAmmo(0);
        playerBoardMessage2.setRedAmmo(2);
        playerBoardMessage2.setNumOfDeaths(1);
        playerBoardMessage2.setFlipped(true);
        playerBoardMessage2.setFirstPlayer(false);
        playerBoardMessage2.setDamageSequence(characters);
        playerBoardMessage2.setMarkSequence(characters);
        playerBoards.add(playerBoardMessage2);
        PlayerBoardMessage playerBoardMessage3 = new PlayerBoardMessage();
        playerBoardMessage3.setYellowAmmo(0);
        playerBoardMessage3.setBlueAmmo(0);
        playerBoardMessage3.setRedAmmo(0);
        playerBoardMessage3.setNumOfDeaths(1);
        playerBoardMessage3.setFlipped(false);
        playerBoardMessage3.setFirstPlayer(true);
        playerBoardMessage3.setDamageSequence(characters1);
        playerBoardMessage3.setMarkSequence(characters1);
        playerBoards.add(playerBoardMessage3);
        matchState.setPlayerBoardMessages(playerBoards);

        List<PlayerHand> playerHands = new ArrayList<>();

        PlayerHand playerHand1 = new PlayerHand();
        playerHand1.setWeaponsUnloaded(weaponsEmpty);
        playerHand1.setWeaponsHidden(2);
        playerHand1.setPowerups(0);
        playerHands.add(playerHand1);

        PlayerHand playerHand2 = new PlayerHand();
        playerHand2.setWeaponsUnloaded(weapons);
        playerHand2.setWeaponsHidden(0);
        playerHand2.setPowerups(3);
        playerHands.add(playerHand2);

        PlayerHand playerHand3 = new PlayerHand();
        playerHand3.setWeaponsUnloaded(weapons);
        playerHand3.setWeaponsHidden(0);
        playerHand3.setPowerups(3);
        playerHands.add(playerHand3);

        matchState.setPlayerHands(playerHands);
        matchState.setKillSequence(characters);
        List<Boolean> booleans = new ArrayList<>();
        booleans.add(true);
        booleans.add(false);
        booleans.add(false);
        booleans.add(true);
        booleans.add(true);
        matchState.setOverkillSequence(booleans);
        matchState.setPowerupsDeckSize(20);
        matchState.setWeaponsDeckSize(10);
        matchState.setCurrentPlayerLeftActions(2);
        prova.setMatchState(matchState);

        if (prova.getAmmoTileDescription().get("AD_ammo_042.png") == null) {
            System.out.println("AMMO NON TROVATA");
        } else {
            System.out.println(prova.getAmmoTileDescription().get("AD_ammo_042.png"));
        }
        PrivateHand privateHand = new PrivateHand(weaponsEmpty, weapons, weapons);
        prova.setPrivateHand(privateHand);
        prova.displayCanIShoot(true);
    }

    public static void restartScanner(){

        in = new Scanner(System.in);
    }


    /**
     * method used to read numbers between max and min from the user
     * @param min smaller number readable
     * @param max biggest number readable
     * @return number read
     */
    public int readNumbers(int min, int max){

        int number;
        do {
            out.println("enter the number corresponding your choice:\n");
            while (!in.hasNextInt()) {
                out.println("That's not a number!\n");
                in.next();
            }
            number = in.nextInt();
        } while (number < min || number > max);
        return number;
    }

    /**
     * use to check if two numbers are equals
     * @param numberTyped number insert by user
     * @param numberToCheck number expected
     * @return true if they are equals false otherwise
     */
    public boolean sameNumbers(int numberTyped, int numberToCheck){

        return (numberTyped == numberToCheck);
    }

    public void displayLoginWindow(){

        out.println("            ,--.   ,--.,------.,--.    ,-----. ,-----. ,--.   ,--.,------.               \n" +
                "            |  |   |  ||  .---'|  |   '  .--./'  .-.  '|   `.'   ||  .---'               \n" +
                "            |  |.'.|  ||  `--, |  |   |  |    |  | |  ||  |'.'|  ||  `--,                \n" +
                "            |   ,'.   ||  `---.|  '--.'  '--'\\'  '-'  '|  |   |  ||  `---.               \n" +
                "            '--'   '--'`------'`-----' `-----' `-----' `--'   `--'`------'               \n" +
                "                                    ,--------. ,-----.                                   \n" +
                "                                    '--.  .--''  .-.  '                                  \n" +
                "                                       |  |   |  | |  |                                  \n" +
                "                                       |  |   '  '-'  '                                  \n" +
                "                                       `--'    `-----'                                   \n" +
                "      ,---.  ,------.  ,------. ,------.,--.  ,--.  ,---.  ,--.   ,--.,--.  ,--.,------. \n" +
                "     /  O  \\ |  .-.  \\ |  .--. '|  .---'|  ,'.|  | /  O  \\ |  |   |  ||  ,'.|  ||  .---' \n" +
                "    |  .-.  ||  |  \\  :|  '--'.'|  `--, |  |' '  ||  .-.  ||  |   |  ||  |' '  ||  `--,  \n" +
                "    |  | |  ||  '--'  /|  |\\  \\ |  `---.|  | `   ||  | |  ||  '--.|  ||  | `   ||  `---. \n" +
                "    `--' `--'`-------' `--' '--'`------'`--'  `--'`--' `--'`-----'`--'`--'  `--'`------'  \n\n\n");
        out.println("PLEASE INSERT YOUR USERNAME:\n");

        String name = in.nextLine();

        while (name.equals("All") || name.isEmpty() || name.matches("^\\s*$")){
            out.println("\nThis username is not allowed, sorry! :(\n\n" +
                            "INSERT ANOTHER ONE:\n");
            name = in.nextLine();
        }

        this.username = name;

        out.println("\nHELLO " + username + " !!!\n" + "\nPLEASE SELECT THE TYPE OF CONNECTION:\n" +
                "1. SOCKET\n" +
                "2. RMI\n");

        int typeOfConnection = readNumbers(1, 2);

        out.println("\nSearching other players...\n\n" +
                "We suggest you to play with the board and the characters near to you,\nyou may need instruction too:\n" +
                "RULES MANUAL: https://czechgames.com/files/rules/adrenaline-rules-en.pdf\n" +
                "WEAPONS MANUAL: https://czechgames.com/files/rules/adrenaline-rules-weapons-en.pdf\n");

        Message loginMes = new Message(username);
        loginMes.createLoginMessage(username, sameNumbers(typeOfConnection, 2));
        client.send(loginMes);
    }

    /**
     * when the turn timer elapses a reconnection window appears to ask to the player if he wants to reconnect
     */
    public void displayReconnectionWindow(){

        out.println("DRIIIIIIN DRIIIIIN\n");
        out.println("       .-.-.\n" +
                "  ((  (__I__)  ))\n" +
                "    .'_....._'.\n" +
                "   / / .12 . \\ \\\n" +
                "  | | '  |  ' | |\n" +
                "  | | 9  /  3 | |\n" +
                "   \\ \\ '.6.' / /\n" +
                "    '.`-...-'.'\n" +
                "     /'-- --'\\\n" +
                "    `\"\"\"\"\"\"\"\"\"`");
        out.println("YOUR TIME IS UP MY FRIEND!\n");
        out.println("\nOoops it looks like you have been disconnected from the game!!!  (˘_˘٥) \n" +
                "PRESS ENTER TO RECONNECT:\n");
        in.nextLine();
        out.println("\nWe are trying to reconnect you to the game... ¯\\_( ͡° ͜ʖ ͡°)_/¯\n");
        Message reconnectionMes = new Message(username);
        reconnectionMes.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
        client.send(reconnectionMes);
    }
    /**
     * shows an alert to tell that a player is disconnected
     * @param indexOfTheDisconnected index
     */
    public void displayPlayerDisconnectedWindow(int indexOfTheDisconnected){

        out.println("\n Oh no! " + usernames.get(indexOfTheDisconnected) + " just disconnected from the game... (✖╭╮✖)\n");
    }

    /**
     * shows a window that tells if the username is not available
     */
    public void displayUsernameNotAvailable(){

        out.println("\nOops, looks like someone has already taken this username! :(\n");
        out.println("PLEASE INSERT ANOTHER USERNAME:\n");

        String name = in.nextLine();

        while (name.equals("All") || name.isEmpty() || name.matches("^\\s*$")){
            out.println("\nThis username is not allowed, sorry! :(\n\n" +
                    "INSERT ANOTHER ONE:\n");
            name = in.nextLine();
        }

        this.username = name;

        out.println("\nPLEASE SELECT AGAIN THE TYPE OF CONNECTION:\n" +
                "1. SOCKET\n" +
                "2. RMI\n");

        int typeOfConnection = readNumbers(1, 2);

        out.println("\nSearching other players...\n\n");

        Message loginMes = new Message(username);
        loginMes.createLoginMessage(username, sameNumbers(typeOfConnection, 2));
        client.send(loginMes);
    }

    /**
     * getting the board json file from int selection
     * @param selection selection
     * @return board json file name
     */
    private String getBoardFile(int selection){
        String board;
        //if the user has chosen medium board I get one of the 2 randomly
        if (selection == 2){
            selection = new Random().nextInt(2) + 2;
            board = boards.get(selection);
        }
        else if (selection == 3){
            board = boards.get(4);
        }
        else {
            board = boards.get(selection);
        }
        return board;
    }

    /**
     * shows the window after the login the one with match setups
     */
    public void displayLoginSuccessful(LoginReport loginReport){

        out.println("\nYES! " + loginReport.getNumberOfPlayers() + " players found.\n" +
                "You are the first of the list so you can choose match settings! ヽ(•‿•)ノ\n\n" +
                "FRENZY MODE?\n" +
                "1. YES\n" +
                "2. NO");
        int frenzy = readNumbers(1, 2);
        out.println("\nEASY MODE? (It means you will play with 5 skulls instead of 8)\n" +
                "1. YES\n" +
                "2. NO");
        int easyMode = readNumbers(1, 2);
        out.println("\nNOW CHOOSE THE BOARD YOU WANT TO PLAY IN:\n" +
                "1. SMALL BOARD (good for 3/4 players)\n" +
                "2. MEDIUM BOARD (good for any number of players)\n" +
                "3. LARGE BOARD (good for 5 players)");
        int boardType = readNumbers(1,3);
        out.println("\nNICE CHOICE!!!\n" +
                "Our imps are building the board... please wait...\n");

        MatchSetup settings = new MatchSetup(sameNumbers(frenzy,1), sameNumbers(easyMode, 1), getBoardFile(boardType));
        Message settingMes = new Message(username);
        settingMes.createMessageMatchSetup(settings);
        client.send(settingMes);
    }

    /**
     * displays for the first time the board with all setting chosen
     * @param matchStart info about the match setting
     */
    public void displayMatchStart(MatchStart matchStart){

        usernames = matchStart.getUsernames();
        characters = matchStart.getCharacters();

        out.println("\nMATCH HAS BEEN CREATED!\n" +
                "The first player has chosen this settings:\n" +
                "FRENZY:" );
        if (matchStart.isFrenzy()){

            out.println("YES\n");
        }

        else {

            out.println("NO\n");
        }

        out.println("MODE: ");

        if (matchStart.isEightSkulls()){

            out.println("NORMAL\n");
        }

        else {

            out.println("EASY (5 skulls)\n");
        }

        out.println("BOARD TYPE:\n" + boardSize.get(matchStart.getBoardType()) + "\n");

        out.println("THESE ARE THE PLAYERS:\n");

        for (int i = 0; i < usernames.size(); i++){

            out.print("\n(　-_･) ︻デ═一  ▸    " + usernames.get(i) + "  using  ");
            printCharacterName(characters.get(i));
        }

        out.println("\n\nTURN OF THE FIRST PLAYER:\n");
    }

    /**
     * show the window with the possible actions
     * @param answer if answer is false player can't click on shoot action
     */
    public void displayCanIShoot(boolean answer){

        int choice = displayOptions(answer);
        Message mes = new Message(username);

        //info window
        if (sameNumbers(choice, 1)){
            displayInfoWindow(answer);
        }
        //use powerup
        else if (sameNumbers(choice, 2 )){
            mes.createAskMessage(TypeOfAction.USEPOWERUP);
        }
        //reload
        else if (sameNumbers(choice, 3)){
            mes.createAskMessage(TypeOfAction.RELOAD);
        }
        //end turn
        else if (sameNumbers(choice, 4)){
            mes.createEndTurnMessage();
        }
        //move
        else if (sameNumbers(choice, 5)){
            mes.createAskMessage(TypeOfAction.MOVE);
        }
        //grab
        else if (sameNumbers(choice, 6)){
            mes.createAskMessage(TypeOfAction.GRAB);
        }
        //shoot
        else if (sameNumbers(choice, 7)){
            mes.createAskMessage(TypeOfAction.SHOOT);
        }

        //if he did not choose info window we send the message
        if (!sameNumbers(choice, 1)) {
            client.send(mes);
        }
    }

    /**
     * ised to show the first display
     * @param answer can I shoot answer
     */
    private void goBackActionWindow(boolean answer){
        out.println("\nPRESS SOMETHING TO GO BACK IN THE ACTION WINDOW:\n");
        in.next();
        displayCanIShoot(answer);
    }

    private void displayInfoWindow(boolean answer){

        out.println("\nWhat do you want to know?\n");
        out.println("╔═════════════════╗\n" +
                "║   MATCH INFO    ║\n" +
                "╠═════════════════╣\n" +
                "║ 1. BATTLE FIELD ║\n" +
                "║ 2. PLAYERS      ║\n" +
                "║ 3. KILL TRACK   ║\n" +
                "║ 4. YOUR STATE   ║\n" +
                "║ 5. GO BACK      ║\n" +
                "╚═════════════════╝");
        int choice = readNumbers(1,5);
        //battle field
        if (sameNumbers(choice, 1)){
           displayBattlefield(answer);
        }
        //Players
        else if (sameNumbers(choice, 2)){
            out.println("╔═════════════════════╗\n" +
                    "║    PLAYERS INFO     ║\n" +
                    "╠═════════════════════╣\n" +
                    "║ 1. PLAYERS' BOARDS  ║\n" +
                    "║ 2. PLAYERS' CARDS   ║\n" +
                    "╚═════════════════════╝");
             choice = readNumbers(1,2);

             //player boards
             if (sameNumbers(choice, 1)){
                 displayPlayerBoards(answer);
             }
             //player cards
            else{
                displayPlayerCards(answer);
             }
        }
        //Kill track
        else if (sameNumbers(choice, 3)){
            displayKillTrack(answer);
        }
        //your status
        else if (sameNumbers(choice, 4)){
            displayYourStatus(answer);
        }
        //go back
        else{
            displayCanIShoot(answer);
        }
    }

    private void displayYourStatus(boolean answer){

        printCharacterName(characters.get(usernames.indexOf(username)));
        out.println("");
        out.println("YOUR CARDS:");
        out.print("unloaded weapons: ");
        for (String weapon: privateHand.getWeaponsUnloaded()){
            out.print("  " + weapon);
        }
        out.print("\nloaded weapons: ");
        for (String weapon: privateHand.getWeaponsLoaded()){
            out.println("    " + weapon);
        }
        out.print("\npowerups: ");
        for (String powerup: privateHand.getPowerups()){
            out.print("    " + powerup);
        }
        out.print("\n\nYOUR LIFE: ");
        PlayerBoardMessage playerBoard = matchState.getPlayerBoardMessages().get(usernames.indexOf(username));
        out.print("DAMAGES: ");
        for (Character character: playerBoard.getDamageSequence()){
            printCharacter(character);
        }
        out.print("\nMARKS: ");
        for (Character character: playerBoard.getMarkSequence()){
            printCharacter(character);
        }
        out.println("\n"+playerBoard.getNumOfDeaths()+"deaths so far");
        out.println("AMMO:  " + ANSI_RED + playerBoard.getRedAmmo() + ANSI_RESET + "  " + ANSI_YELLOW + playerBoard.getYellowAmmo() + ANSI_RESET + "  " + ANSI_BLUE + playerBoard.getBlueAmmo() + ANSI_RESET);
        if (playerBoard.isFirstPlayer()){
            out.println("FIRST PLAYER");
        }
        if (playerBoard.isFlipped()){
            out.println("BOARD FLIPPED");
        }
        goBackActionWindow(answer);
    }

    /**
     * shows the kill track
     * @param answer can I shoot
     */
    private void displayKillTrack(boolean answer){

        out.println("KILL TRACK:\n");
        for (int i = 0; i < matchState.getKillSequence().size(); i++){

            printCharacterName(matchState.getKillSequence().get(i));
            if (matchState.getOverkillSequence().get(i)){
                out.println("(OVERKILL) ");
            }
            else {
                out.println("");
            }
        }
        goBackActionWindow(answer);
    }

    /**
     * shows the status of the player boards
     * @param answer can I shoot
     */
    private void displayPlayerBoards(Boolean answer){

        displayLegend();
        out.println("PLAYER BOARDS:\n");
        for (PlayerBoardMessage playerBoard: matchState.getPlayerBoardMessages()){

            //excluding the player
            if (matchState.getPlayerBoardMessages().indexOf(playerBoard) != usernames.indexOf(username)){

                printCharacterName(characters.get(matchState.getPlayerBoardMessages().indexOf(playerBoard)));
                out.println("");
                out.print("DAMAGES: ");
                for (Character character: playerBoard.getDamageSequence()){
                    printCharacter(character);
                }
                out.print("\nMARKS: ");
                for (Character character: playerBoard.getMarkSequence()){
                    printCharacter(character);
                }
                out.println("\nAMMO:  " + ANSI_RED + playerBoard.getRedAmmo() + ANSI_RESET + "  " + ANSI_YELLOW + playerBoard.getYellowAmmo() + ANSI_RESET + "  " + ANSI_BLUE + playerBoard.getBlueAmmo() + ANSI_RESET);
                if (playerBoard.isFirstPlayer()){
                    out.println("FIRST PLAYER");
                }
                if (playerBoard.isFlipped()){
                    out.println("BOARD FLIPPED");
                }
                out.println(playerBoard.getNumOfDeaths()+" deaths so far\n");
            }
        }
        goBackActionWindow(answer);
    }

    private void displayPlayerCards(Boolean answer){

        out.println("PLAYERS' CARDS:\n");
        for (PlayerHand playerHand: matchState.getPlayerHands()){

            //excluding the player
            if (matchState.getPlayerHands().indexOf(playerHand) != usernames.indexOf(username)) {
                printCharacterName(characters.get(matchState.getPlayerHands().indexOf(playerHand)));
                out.println("");
                out.println("WEAPONS LOADED: " + playerHand.getWeaponsHidden());
                out.print("WEAPONS UNLOADED: ");
                for (String weapon : playerHand.getWeaponsUnloaded()) {
                    out.print(weapon + "      ");
                }
                out.println("\nPOWERUPS: " + playerHand.getPowerups() + "\n");
            }
        }
        goBackActionWindow(answer);
    }

    /**
     * displays the legend of the colors related to the characters
     */
    private void displayLegend(){

        out.println("LEGEND:\n\n" + ANSI_YELLOW + "DISTRUCTOR" + ANSI_RESET);
        out.println(ANSI_BLUE + "BANSHEE" + ANSI_RESET);
        out.println(ANSI_WHITE + "DOZER" + ANSI_RESET);
        out.println(ANSI_PURPLE + "VIOLET" + ANSI_RESET);
        out.println(ANSI_GREEN + "SPROG\n" + ANSI_RESET);
    }

    /**
     * takes a character and display his color with his initial
     * @param characterName character
     */
    private void printCharacter(Character characterName){

        switch (characterName){

            case DISTRUCTOR:
                out.print(ANSI_YELLOW + "D " + ANSI_RESET);
                break;
            case DOZER:
                out.print(ANSI_WHITE + "DO " + ANSI_RESET);
                break;
            case BANSHEE:
                out.print(ANSI_BLUE + "B " + ANSI_RESET);
                break;
            case SPROG:
                out.print(ANSI_GREEN + "S " + ANSI_RESET);
                break;
            case VIOLET:
                out.print(ANSI_PURPLE + "V " + ANSI_RESET);
                break;
        }
    }

    /**
     * takes a character and display his color with his name
     * @param characterName character
     */
    private void printCharacterName(Character characterName){

        switch (characterName){

            case DISTRUCTOR:
                out.print(ANSI_YELLOW + "DISTRUCTOR " + ANSI_RESET);
                break;
            case DOZER:
                out.print(ANSI_WHITE + "DOZER " + ANSI_RESET);
                break;
            case BANSHEE:
                out.print(ANSI_BLUE + "BANSHEE " + ANSI_RESET);
                break;
            case SPROG:
                out.print(ANSI_GREEN + "SPROG " + ANSI_RESET);
                break;
            case VIOLET:
                out.print(ANSI_PURPLE + "VIOLET " + ANSI_RESET);
                break;
        }
    }


    private void displayBattlefield(boolean answer){

        out.println( "\n" + matchState.getWeaponsDeckSize() + " weapons and " + matchState.getPowerupsDeckSize() + " powerups left in the deck\n");
        out.println("CELLS OF THE BOARD:\n");

        for (MessageCell cell: matchState.getCells()){

            out.println("CELL " + matchState.getCells().indexOf(cell));
            out.println("row: " + cell.getRow());
            out.println("column: " + cell.getColumn());
            out.print("players inside: ");
            if (cell.getCharacters().isEmpty()){
                out.println("nobody is inside this cell  :(");
            }
            else {
                for (Character character : cell.getCharacters()) {
                    printCharacterName(character);

                }
            }
            if (cell.isEmpty()){

                out.println("\nThis cell is empty\n");
            }

            //common cell
            else if (cell.getAmmoTile() != null ){

                out.println("ammo tile: " + ammoTileDescription.get(cell.getAmmoTile()));
            }

            else {

                out.print("weapons inside: ");
                for(String weaponName: cell.getWeapons()){
                    out.print(weaponName + "   ");
                }
                out.println("\n");
            }
        }
        goBackActionWindow(answer);
    }

    /**
     * shows the options based on current player left actions
     * @param answer tells if he can shoot
     * @return the option chosed
     */
    public int displayOptions(boolean answer){

        int choice;

        if (matchState.getCurrentPlayerLeftActions() > 0){

            if (matchState.getCurrentPlayerLeftActions() == 2){

                out.println("IT'S YOUR TURN CHOOSE AN ACTION:       (remember you only have 8 minutes!!!)\n");
            }

            else{

                out.println("IT'S STILL YOUR TURN, CHOOSE YOUR NEXT ACTION:\n");
            }

            //he can shoot
            if (answer){

                out.println("╔═════════════════════════╗\n" +
                        "║        OPTIONS:         ║\n" +
                        "╠═════════════════════════╣\n" +
                        "║ 1. INFO ABOUT THE MATCH ║\n" +
                        "║ 2. USE POWERUP          ║\n" +
                        "║ 3. RELOAD               ║\n" +
                        "║ 4. END TURN             ║\n" +
                        "║ 5. MOVE                 ║\n" +
                        "║ 6. GRAB                 ║\n" +
                        "║ 7. SHOOT                ║\n" +
                        "╚═════════════════════════╝\n");

                choice = readNumbers(1,7);
            }

            else {

                out.println("\nWe are sorry you can't shoot now! But you can do other cool things.    (˵ ͡° ͜ʖ ͡°˵) \n");
                out.println("╔═════════════════════════╗\n" +
                        "║        OPTIONS:         ║\n" +
                        "╠═════════════════════════╣\n" +
                        "║ 1. INFO ABOUT THE MATCH ║\n" +
                        "║ 2. USE POWERUP          ║\n" +
                        "║ 3. RELOAD               ║\n" +
                        "║ 4. END TURN             ║\n" +
                        "║ 5. MOVE                 ║\n" +
                        "║ 6. GRAB                 ║\n" +
                        "╚═════════════════════════╝\n");
                choice = readNumbers(1,6);
            }
        }

        //no more actions available (left actions = 0)
        else {

            out.println("You have used all your actions WELL DONE!\n" +
                    "You can still do something!!\n" +
                    "TIPS: don't forget to reload your unloaded weapons before ending you turn...  ︻╦╤─ ︻╦╤─ ︻╦╤─\n");
            out.println("╔═════════════════════════╗\n" +
                    "║        OPTIONS:         ║\n" +
                    "╠═════════════════════════╣\n" +
                    "║ 1. INFO ABOUT THE MATCH ║\n" +
                    "║ 2. USE POWERUP          ║\n" +
                    "║ 3. RELOAD               ║\n" +
                    "║ 4. END TURN             ║\n" +
                    "╚═════════════════════════╝");
            choice = readNumbers(1,4);
        }

        return choice;
    }

    /**
     * show the cells the player can click (player must click one)
     * @param cells List of coords of the cell that can be choosed
     */
    public void displayAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction){

        //TODO implement
    }

    /**
     * shows the cards the player can choose
     * @param cards contains options
     */
    public void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction){

        //TODO implement
    }

    /**
     * shows the cards the player can choose with the "no" button
     * @param cards contains options
     */
    public void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction){

        //TODO implement
    }

    /**
     * shows the characters the player can choose
     * @param players contains options
     */
    public void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction){

        //TODO implement
    }

    /**
     * shows the characters the player can choose with the "no" button
     * @param players contains options
     */
    public void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction){

        //TODO implement
    }

    /**
     * shows the effects the player can choose
     * @param effects contains options
     */
    public void displayAvailableEffects(List<IndexMessage> effects){

        //TODO implement
    }

    /**
     * shows the effects the player can choose with the "no" button
     * @param effects contains options
     */
    public void displayAvailableEffectsWithNoOption(List<IndexMessage> effects){

        //TODO implement
    }

    /**
     * show the payment message with the powerups that can be used to pay
     * @param paymentInfo if must is true the player has to choose one, otherwise he can click "no" button
     */
    public void displayPayment(PaymentMessage paymentInfo){

        //TODO implement
    }

    /**
     * like the method before, but this method, if the player select "pay with ammo" button shows a window where
     * the player has to select a color (between the available ones) to pay an ammo cube
     * @param paymentMessage payment info, if must is true then no answer is not accepted
     */
    public void displayPaymentForPowerupsCost(PaymentMessage paymentMessage){

        //TODO implement
    }

    /**
     * updates all the info showed in cli and gui about the status of the match
     * @param matchState updates all over the board and the cards
     */
    public void updateMatchState(MatchState matchState){

        this.matchState = matchState;

        //TODO implement
    }

    /**
     * updates all the info about the end of the player
     * @param privateHand the hand of the player who is playing
     */
    public void updatePrivateHand(PrivateHand privateHand){

        //TODO implement
    }

    /**
     * shows the end of the match with the winner
     * @param leaderBoard contains the leader board with points and characters
     */
    public void displayEndMatchLeaderBoard(LeaderBoard leaderBoard){

        //TODO implement
    }


}
