package it.polimi.sw2019.view;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author poligenius
 * SIMPLY the CLI
 */
public class CLI implements ViewInterface {

    /**
     * Customized constructor
     * @param client reference to the client
     */
    public CLI(Client client){

        setClient(client);
        Gson json = new Gson();
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/AmmoTiles/AmmoTileDescription.json")));
            ammoTileDescription = json.fromJson(jsonReader, Map.class);
        }
        catch (Exception e){
            LOGGER.log(Level.SEVERE, "file not found");
        }

    }

    /**
     * Default constructor for test
     */
    public CLI() {

        Gson json = new Gson();
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/AmmoTiles/AmmoTileDescription.json")));
            ammoTileDescription = json.fromJson(jsonReader, Map.class);
        }
        catch (Exception e){
            LOGGER.log(Level.SEVERE, "file not found");
        }
    }

    /* Attributes */

    private MatchState matchState; //the current matchState

    private PrivateHand privateHand; //the current privateHand

    private BoardCoord lastCellSelected;

    private Boolean lastCanIshoot;

    private Boolean damageSession;

    private String username; //player user

    private List<String> usernames = new ArrayList<>(); //username of all players

    private List<Character> characters = new ArrayList<>();

    private boolean firstMatch = true;

    private int turnDuration;

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

    public void setLastCanIshoot(Boolean lastCanIshoot) {
        this.lastCanIshoot = lastCanIshoot;
    }

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

/*
    public static void main(String[] args){

        CLI prova = new CLI();

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
        weapons.add("T.H.O.R");
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
        matchState.setCurrentPlayer(Character.DISTRUCTOR);
        prova.setMatchState(matchState);

        List<Colors> colors = new ArrayList<>();
        colors.add(Colors.BLUE);
        colors.add(Colors.YELLOW);
        colors.add(Colors.RED);
        PrivateHand privateHand = new PrivateHand(weaponsEmpty, weapons, weapons, weapons, colors);
        prova.setPrivateHand(privateHand);
        Message matchSetup = new Message("All");
        matchSetup.createMessageMatchSetup(new MatchSetup(false, false, "Board1.json"));

        List<BoardCoord> coords = new ArrayList<>();
        coords.add(new BoardCoord(0,1));
        coords.add(new BoardCoord(2,3));
        coords.add(new BoardCoord(1, 2));
        coords.add(new BoardCoord(0, 0));


        List<IndexMessage> indexMessages = new ArrayList<>();
        indexMessages.add(new IndexMessage(0));
        indexMessages.add(new IndexMessage(2));
        AvailableCards cards = new AvailableCards(indexMessages, false);
        prova.setLastCanIshoot(false);
        PaymentMessage paymentMessage = new PaymentMessage(false, indexMessages);
        MatchState matchState1 = new MatchState();
        matchState1.setCurrentPlayer(Character.DOZER);
        Score score = new Score(characters, new KillTokens(characters));
        score.addPoints(17, Character.DISTRUCTOR);
        score.addPoints(0, Character.BANSHEE);
        score.addPoints(4, Character.VIOLET);
        score.addPoints(6, Character.DOZER);
        score.addPoints(18, Character.SPROG);
        LeaderBoard leaderBoard = new LeaderBoard(score.getRankingMap(), score.getMap());
        AvailableEffects availableEffects = new AvailableEffects(indexMessages, weapons);
        String report = "  KILLED ☠☠☠☠☠  ";
        ActionReports actionReports = new ActionReports(report, characters.get(0), characters.get(2));

        //prova.displayAlreadyConnectedWindow();
        //prova.displayActionReport(actionReports);
        //prova.displayAvailableEffectsWithNoOption(availableEffects);
        //prova.displayAvailableEffects(availableEffects);
        //prova.displayEndMatchLeaderBoard(leaderBoard);
        //prova.updateMatchState(matchState1);
        //prova.displayPaymentForPowerupsCost(paymentMessage);
        //prova.displayPayment(paymentMessage);
        //prova.displayAvailablePlayersWithNoOption(characters, TypeOfAction.SHOOT);
        //prova.displayAvailablePlayers(characters, TypeOfAction.SHOOT);
        //prova.displayAvailableCardsWithNoOption(cards, TypeOfAction.USEPOWERUP);
        //prova.displayAvailableCards(cards, TypeOfAction.SPAWN);
        //prova.displayAvailableCells(coords, TypeOfAction.MOVE);
        //prova.displayMatchStart(new MatchStart(matchSetup, usernames, characters));
        //prova.displayCanIShoot(true);
        //prova.displayReconnectionWindow();
        //prova.displayMatchStart(new MatchStart(matchSetup, usernames, characters));
        //prova.displayUsernameNotAvailable();
        //prova.displayLoginSuccessful(new LoginReport(4));
        //prova.displayLoginWindow();
        prova.displayDisconnectionDuringSetup();
    }
    */


    /**
     * used to reset all the values
     */
    private void clearAttributes(){

        setLastCanIshoot(null);
        setPrivateHand(null);
        setMatchState(null);
        setCharacters(null);
        setUsername(null);
        setUsernames(null);
        firstMatch = false;
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

        out.println("\n\n\n                ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗         \n" +
                "                ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝         \n" +
                "                ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗           \n" +
                "                ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝           \n" +
                "                ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗         \n" +
                "                 ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝         ");

        try {
            TimeUnit.MILLISECONDS.sleep(1200);
            out.println("                                    ████████╗ ██████╗                                  \n" +
                    "                                    ╚══██╔══╝██╔═══██╗                                 \n" +
                    "                                       ██║   ██║   ██║                                 \n" +
                    "                                       ██║   ██║   ██║                                 \n" +
                    "                                       ██║   ╚██████╔╝                                 \n" +
                    "                                       ╚═╝    ╚═════╝                                  ");

            TimeUnit.MILLISECONDS.sleep(1200);
            out.println("         █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗ █████╗ ██╗     ██╗███╗   ██╗███████╗\n" +
                    "        ██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██╔══██╗██║     ██║████╗  ██║██╔════╝\n" +
                    "        ███████║██║  ██║██████╔╝█████╗  ██╔██╗ ██║███████║██║     ██║██╔██╗ ██║█████╗  \n" +
                    "        ██╔══██║██║  ██║██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║     ██║██║╚██╗██║██╔══╝  \n" +
                    "        ██║  ██║██████╔╝██║  ██║███████╗██║ ╚████║██║  ██║███████╗██║██║ ╚████║███████╗\n" +
                    "        ╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝╚═╝  ╚═══╝╚══════╝\n" +
                    "                                                                                       ");
            TimeUnit.MILLISECONDS.sleep(1200);
        }
        catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "interrupted exception");
            Thread.currentThread().interrupt();
        }

        out.println("PLEASE INSERT YOUR USERNAME:\n");

        if (!firstMatch) {
            in.nextLine(); //to solve a bug where message after popped too soon
        }

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
        client.connect(loginMes);
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
        out.println("\nOoops it looks like you have been disconnected from the game!!!  (˘_˘٥) \n");
               // "PRESS ENTER TO RECONNECT:\n");
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

        if ( !usernames.isEmpty() && usernames.size() > indexOfTheDisconnected) {
            out.println("\n Oh no! " + usernames.get(indexOfTheDisconnected) + " just disconnected from the game... (✖╭╮✖)\n");
        }
    }

    /**
     * shows a window that tells if the username is not available
     */
    public void displayUsernameNotAvailable(){

        out.println("\nOops, looks like someone has already taken this username! :(\n");
        out.println("PLEASE INSERT ANOTHER USERNAME:\n");

        in.nextLine();
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

        try {
            client.connect(loginMes);
        }
        catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error in connect");
        }
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

        out.println("YOU HAVE ONLY ONE MINUTE TO CHOOSE!");
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

        //saving turn duration in seconds
        Long longDuration = (matchStart.getTurnDuration()/60000);
        turnDuration = longDuration.intValue();
    }

    /**
     * show the window with the possible actions
     * @param answer if answer is false player can't click on shoot action
     */
    public void displayCanIShoot(boolean answer){

        int choice = displayOptions(answer);
        lastCanIshoot = answer;
        damageSession = false;
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
     * used to show the first display
     * @param answer can I shoot answer
     */
    private void goBackActionWindow(boolean answer){
        out.println("\nPRESS SOMETHING TO GO BACK IN THE ACTION WINDOW:\n");
        in.next();
        displayCanIShoot(answer);
    }

    /**
     * display the info window
     * @param answer can I shoot received
     */
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

    /**
     * display your status window
     * @param answer canIshoot received
     */
    private void displayYourStatus(boolean answer){

        printCharacterName(characters.get(usernames.indexOf(username)));
        out.println("");
        out.println("YOUR CARDS:");
        out.print("unloaded weapons: ");
        for (String weapon: privateHand.getWeaponsUnloaded()){
            out.print("    " + weapon);
        }
        out.print("\nloaded weapons: ");
        for (String weapon: privateHand.getWeaponsLoaded()){
            out.print("    " + weapon);
        }
        out.print("\npowerups: ");
        for (String powerup: privateHand.getPowerups()){
            out.print("    " );
            printPowerupName(privateHand.getPowerups().indexOf(powerup));
            out.print("");
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

    /**
     * dysplay the cards of the player
     * @param answer canIshoot received
     */
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
                default:
                    LOGGER.log(Level.SEVERE, "CHARACTER NOT FOUND");
        }
    }

    /**
     * Used to print powerup names colored
     * @param index of the powerup
     */
    private void printPowerupName(int index){

        String powerupName = privateHand.getPowerups().get(index);

        Colors color = privateHand.getPowerupColors().get(index);

        if (color == Colors.BLUE){
            out.print(ANSI_BLUE + powerupName + ANSI_RESET);
        }
        else if (color == Colors.RED){
            out.print(ANSI_RED + powerupName + ANSI_RESET);
        }
        else{
            out.print(ANSI_YELLOW + powerupName + ANSI_RESET);
        }

    }


    /**
     * displays the board
     * @param answer canIshoot received
     */
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
                out.println();
            }
            if (cell.isEmpty()){

                out.println("\nThis cell is empty\n");
            }

            //common cell
            else if (cell.getAmmoTile() != null ){

                out.println("ammo tile: " + ammoTileDescription.get(cell.getAmmoTile()) + "\n");
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

                out.println("IT'S YOUR TURN CHOOSE AN ACTION:       (remember you only have " + turnDuration + " minutes!!!)\n");
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

        out.println("\nCHOOSE THE CELL: ");
        int cont  = 0;
        for (BoardCoord cell: cells){
            out.println(cont + ".  row: " + cell.getRow() + "  column: " + cell.getColumn());
            cont++;
        }
        int choice = readNumbers(0, cells.size() - 1);
        Message selectedCell = new Message(username);

        if ( typeOfAction == TypeOfAction.SHOOT){

            selectedCell.createSelectedCellMessage(cells.get(choice), typeOfAction, TypeOfMessage.SELECTED_CELL);
        }

        else {

            selectedCell.createSelectedCellMessage(cells.get(choice), typeOfAction, TypeOfMessage.SINGLE_ACTION);
        }

        lastCellSelected = cells.get(choice);

        client.send(selectedCell);
    }

    /**
     * shows the cards the player can choose
     * @param cards contains options
     */
    @SuppressWarnings("Duplicates")
    public void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction){

        int cont = 0;

        if (cards.areWeapons()){
            out.println("CHOOSE THE WEAPON:");

            if (typeOfAction == TypeOfAction.GRAB){
                MessageCell spawnCell = new MessageCell();

                for (MessageCell cell: matchState.getCells()){
                    if (cell.getColumn() == lastCellSelected.getColumn() && cell.getRow() == lastCellSelected.getRow()){
                        spawnCell = cell;
                        break;
                    }
                }

                for(IndexMessage indexMessage: cards.getAvailableCards()){
                    out.println(cont + ".  " + spawnCell.getWeapons().get(indexMessage.getSelectionIndex()));
                    cont++;
                }
            }

            else {
                for (IndexMessage indexMessage : cards.getAvailableCards()) {
                    out.println(cont + ".  " + privateHand.getAllWeapons().get(indexMessage.getSelectionIndex()));
                    cont++;
                }
            }
        }

        else {
            if (typeOfAction == TypeOfAction.SPAWN){
                out.println("\n   YOU ARE DEAD!!!");
                out.println(ANSI_GREEN + "     _.--\"\"--._\n" +
                        "    /  _    _  \\\n" +
                        " _  ( (_\\  /_) )  _\n" +
                        "{ \\._\\   /\\   /_./ }\n" +
                        "/_\"=-.}______{.-=\"_\\\n" +
                        " _  _.=(\"\"\"\")=._  _\n" +
                        "(_'\"_.-\"`~~`\"-._\"'_)\n" +
                        " {_\"            \"_}" + ANSI_RESET);
                out.println("\nYou will spawn in the spawn cell of the same color of the powerup chosen\n");
            }
            out.println("CHOOSE THE POWERUP:");
            for(IndexMessage indexMessage: cards.getAvailableCards()){
                out.print(cont + ".  ");
                printPowerupName(indexMessage.getSelectionIndex());
                out.println("");
                cont++;
            }
        }

        int choice = readNumbers(0, cont - 1);

        int cont2 = 0;
        Message selectedCard = new Message(username);

        if (typeOfAction == TypeOfAction.GRAB){

            //the players has 3 weapons
            if (privateHand.getAllWeapons().size() == 3) {

                out.println("We are sorry you can't have more than 3 weapons, choose one to discard:");

                for (String weapon: privateHand.getAllWeapons()) {
                    out.println(cont2 + ".  " + weapon);
                    cont2++;
                }
                int choice2 = readNumbers(0, cont2 - 1);
                selectedCard.createSingleActionGrabWeapon(new GrabWeapon(cards.getAvailableCards().get(choice).getSelectionIndex(), choice2, lastCellSelected));
            }
            //no weapons to drop
            else{
                selectedCard.createSingleActionGrabWeapon(new GrabWeapon(cards.getAvailableCards().get(choice).getSelectionIndex(), -1, lastCellSelected));
            }
        }

        else {
            out.println(cards.getAvailableCards().get(choice).getSelectionIndex());

            selectedCard.createSelectedCard(cards.getAvailableCards().get(choice).getSelectionIndex(), typeOfAction);
        }

        client.send(selectedCard);
    }

    /**
     * shows the cards the player can choose with the "no" button (used for reload and use powerup
     * @param cards contains options
     */
    @SuppressWarnings("Duplicates")
    public void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction){

        int cont = 0;
        Message selectedCard = new Message(username);

        if (cards.getAvailableCards().isEmpty()){
            out.println("THERE ARE NO CARDS AVAILABLE, DO SOMETHING ELSE.");
        }

        else if (cards.areWeapons()){
            out.println("CHOOSE THE WEAPON:");
            for(IndexMessage indexMessage: cards.getAvailableCards()){
                out.println(cont + ".  " + privateHand.getAllWeapons().get(indexMessage.getSelectionIndex()));
                cont++;
            }
        }

        else {
            if (matchState.getCurrentPlayer() != characters.get(usernames.indexOf(username))){
                out.println("OH NO! A PLAYER JUST SHOT YOU, BUT YOU CAN USE A POWERUP   (⌐■_■)︻╦╤─ (╥﹏╥)");
            }
            out.println("CHOOSE THE POWERUP:");
            for(IndexMessage indexMessage: cards.getAvailableCards()){
                out.print(cont + ".  ");
                printPowerupName(indexMessage.getSelectionIndex());
                out.println("");
                cont++;
            }
        }
        out.println(cont + ". NO THANKS");
        int choice = readNumbers(0, cont);

        if (typeOfAction == TypeOfAction.RELOAD || typeOfAction == TypeOfAction.GRAB){

            //no thanks if it is a grab I'm sure I enter in this if because there are no weapons to draw
            if (sameNumbers(choice, cont)){
                displayCanIShoot(lastCanIshoot);
            }

            else {
                selectedCard.createSingleActionReload(cards.getAvailableCards().get(choice).getSelectionIndex());
                client.send(selectedCard);
            }
        }

        else if (typeOfAction == TypeOfAction.USEPOWERUP){

            //no thanks
            if (sameNumbers(choice, cont)){
                if (damageSession) {
                    selectedCard.createSelectionForUsePowerup(-1);
                    client.send(selectedCard);
                }
                else {
                    displayCanIShoot(lastCanIshoot);
                }
            }
            else{
                selectedCard.createSelectionForUsePowerup(cards.getAvailableCards().get(choice).getSelectionIndex());
                client.send(selectedCard);
            }

        }
    }

    /**
     * shows the characters the player can choose
     * @param players contains options
     */
    @SuppressWarnings("Duplicates")
    public void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction){

        Message selectedPlayer = new Message(username);
        int cont = 0;
        out.println("CHOOSE THE PLAYER:");
        for(Character player: players){
            out.print(cont + ".  ");
            printCharacterName(player);
            out.println("");
            cont++;
        }
        int choice = readNumbers(0, cont-1);
        selectedPlayer.createSelectedPlayer(characters.indexOf(players.get(choice)), typeOfAction);
        client.send(selectedPlayer);
    }

    /**
     * shows the characters the player can choose with the "no" button
     * @param players contains options
     */
    @SuppressWarnings("Duplicates")
    public void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction){

        Message selectedPlayer = new Message(username);
        int cont = 0;

        out.println("CHOOSE THE PLAYER:");
        for(Character player: players){
            out.print(cont + ".  ");
            printCharacterName(player);
            out.println("");
            cont++;
        }
        out.println(cont + ".  NO THANKS");
        int choice = readNumbers(0, cont);
        int indexSelected;
        //NO THANKS
        if (choice == cont){
            indexSelected = -1;
        }
        else {
            indexSelected = characters.indexOf(players.get(choice));
        }
        selectedPlayer.createSelectedPlayer(indexSelected, typeOfAction);
        client.send(selectedPlayer);
    }


    /**
     * shows the effects the player can choose
     * @param availableEffects contains options
     */
    public void displayAvailableEffects(AvailableEffects availableEffects){

        Message selection = new Message(username);
        int cont = 0;
        out.println("CHOOSE THE EFFECT:");
        for (IndexMessage effect: availableEffects.getIndexes()){

            out.println(cont + ". " + availableEffects.getNames().get(availableEffects.getIndexes().indexOf(effect)));
            cont++;
        }
        int choice = readNumbers(0, cont - 1);
        selection.createSelectedEffect(availableEffects.getIndexes().get(choice).getSelectionIndex());
        client.send(selection);
    }

    /**
     * shows the effects the player can choose with the "no" button
     * @param availableEffects contains options
     */
    public void displayAvailableEffectsWithNoOption(AvailableEffects availableEffects){

        Message selection = new Message(username);
        int cont = 0;
        out.println("CHOOSE THE EFFECT:  (select 'NO THANKS' if you don't want to use any effect) ");
        for (IndexMessage effect: availableEffects.getIndexes()){
            out.println(cont + ". " + availableEffects.getNames().get(availableEffects.getIndexes().indexOf(effect)));
            cont++;
        }
        out.println(cont + ".  NO THANKS");
        int choice = readNumbers(0, cont);
        if (sameNumbers(choice, cont)){
            selection.createSelectedEffect(-1);
        }
        else {
            selection.createSelectedEffect(availableEffects.getIndexes().get(choice).getSelectionIndex());
        }
        client.send(selection);
    }

    /**
     * show the payment message with the powerups that can be used to pay
     * @param paymentInfo if must is true the player has to choose one, otherwise he can click "no" button
     */
    @SuppressWarnings("Duplicates")
    public void displayPayment(PaymentMessage paymentInfo){

        int cont = 0;
        int choice;
        Message payment = new Message(username);

        out.println("YOU CAN PAY THE COST WITH ONE OR MORE POWERUPS!!!  <(^_^)>");
        if (!paymentInfo.isMustPay()) {
            out.println("select 'NO THANKS' if you don't want to pay using powerups");
        }
        out.println("SELECT THE POWERUPS:");
        for(IndexMessage indexMessage: paymentInfo.getUsablePowerups()){
            out.print(cont + ".  ");
            printPowerupName(indexMessage.getSelectionIndex());
            out.println("");
            cont++;
        }
        // has enough ammo
        if (!paymentInfo.isMustPay()){
            out.println(cont + ".  NO THANKS");
            choice = readNumbers(0, cont);
        }
        //has not enough ammo
        else{
            choice = readNumbers(0, cont - 1);
        }

        if (sameNumbers(choice, cont)){
            payment.createPaymentSelection(-1);
        }
        else {
            payment.createPaymentSelection(paymentInfo.getUsablePowerups().get(choice).getSelectionIndex());
        }

        client.send(payment);
    }

    /**
     * like the method before, but this method, if the player select "pay with ammo" button shows a window where
     * the player has to select a color (between the available ones) to pay an ammo cube
     * @param paymentMessage payment info, if must is true then no answer is not accepted
     */
    @SuppressWarnings({"Duplicates","squid:S3776"})
    public void displayPaymentForPowerupsCost(PaymentMessage paymentMessage){

        int cont = 0;
        int choice;
        Message payment = new Message(username);

        out.println("\nYOU CAN PAY THE COST OF TARGETING SCOPE WITH ONE OR MORE POWERUPS!!!  <(^_^)>");
        if (!paymentMessage.isMustPay()) {
            out.println("select 'NO THANKS' if you don't want to pay using powerups");
        }
        out.println("SELECT THE POWERUPS:");
        for(IndexMessage indexMessage: paymentMessage.getUsablePowerups()){
            out.print(cont + ".  ");
            printPowerupName(indexMessage.getSelectionIndex());
            out.println("");
            cont++;
        }
        // has enough ammo
        if (!paymentMessage.isMustPay()){
            out.println(cont + ".  NO THANKS");
            choice = readNumbers(0, cont);
        }
        //has not enough ammo
        else{
            choice = readNumbers(0, cont - 1);
        }
        //he pays with cube ammo and have to select the color
        if (sameNumbers(choice, cont)){

            Colors selectedColor = null;
            int cont2 = 1;
            int redInt = -1;
            int blueInt = -1;
            int yellowInt = -1;
            PlayerBoardMessage hisBoard = matchState.getPlayerBoardMessages().get(usernames.indexOf(username));
            out.println("\n PLEASE SELECT THE COLOR OF THE AMMO CUBE YOU WANT TO USE");
            if ( hisBoard.getRedAmmo() > 0) {
                out.println(cont2 + ". " + ANSI_RED + "RED" + ANSI_RESET);
                redInt = cont2;
                cont2++;
            }
            if (hisBoard.getBlueAmmo() > 0) {
                out.println(cont2 + ". " + ANSI_BLUE + "BLUE" + ANSI_RESET);
                blueInt = cont2;
                cont2++;
            }
            if (hisBoard.getYellowAmmo() > 0) {
                out.println(cont2 + ". " + ANSI_YELLOW + "YELLOW" + ANSI_RESET);
                yellowInt = cont2;
                cont2++;
            }
            int colorChoice = readNumbers(1,cont2-1);

            if (sameNumbers(colorChoice,redInt)){
                selectedColor = Colors.RED;
            }
            else if (sameNumbers(colorChoice, blueInt)){
                selectedColor = Colors.BLUE;
            }
            else if (sameNumbers(colorChoice, yellowInt)){
                selectedColor = Colors.YELLOW;
            }
            payment.createColorSelection(selectedColor);
        }
        else {
            payment.createPaymentSelection(paymentMessage.getUsablePowerups().get(choice).getSelectionIndex());
        }

        client.send(payment);
    }

    /**
     * updates all the info showed in cli and gui about the status of the match
     * @param matchStateNew updates all over the board and the cards
     */
    public void updateMatchState(MatchState matchStateNew){

        if (matchState == null || matchStateNew.getCurrentPlayer() != matchState.getCurrentPlayer()){

            out.print("\nIT'S THE TURN OF ");
            printCharacterName(matchStateNew.getCurrentPlayer());
            out.println("");
        }

        matchState = matchStateNew;
    }

    /**
     * updates all the info about the end of the player
     * @param privateHand the hand of the player who is playing
     */
    public void updatePrivateHand(PrivateHand privateHand){

        this.privateHand = privateHand;
    }

    /**
     * shows the end of the match with the winner
     * @param leaderBoard contains the leader board with points and characters
     */
    public void displayEndMatchLeaderBoard(LeaderBoard leaderBoard){

        try{
        out.println("\n\nMATCH IS ENDED!!!    ⊂(◉‿◉)つ\n");

            TimeUnit.MILLISECONDS.sleep(400);

            out.println("...\n");

            TimeUnit.MILLISECONDS.sleep(400);


            out.println("...\n");

            TimeUnit.MILLISECONDS.sleep(400);


            out.println("...\n");

            TimeUnit.MILLISECONDS.sleep(1000);


            out.println("\n\n       ___   ___  _   _  __  __  ___   ___   _     _         \n" +
                    " _/\\_ |   \\ | _ \\| | | ||  \\/  || _ \\ / _ \\ | |   | |    _/\\_\n" +
                    " >  < | |) ||   /| |_| || |\\/| ||   /| (_) || |__ | |__  >  <\n" +
                    "  \\/  |___/ |_|_\\ \\___/ |_|  |_||_|_\\ \\___/ |____||____|  \\/ \n" +
                    "                                                             \n\n");


            TimeUnit.MILLISECONDS.sleep(1600);
        }
        catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "interrupted exception");
            Thread.currentThread().interrupt();
        }

        //he won
        if (!characters.isEmpty() && !usernames.isEmpty() && leaderBoard.getLeaderBoard().get(characters.get(usernames.indexOf(username))) == 1 && !isADraw(leaderBoard)){

            out.println("                         /$$     /$$ /$$$$$$  /$$   /$$                  \n" +
                    "                        |  $$   /$$//$$__  $$| $$  | $$                  \n" +
                    "                         \\  $$ /$$/| $$  \\ $$| $$  | $$                  \n" +
                    "                          \\  $$$$/ | $$  | $$| $$  | $$                  \n" +
                    "                           \\  $$/  | $$  | $$| $$  | $$                  \n" +
                    "                            | $$   | $$  | $$| $$  | $$                  \n" +
                    "                            | $$   |  $$$$$$/|  $$$$$$/                  \n" +
                    "                            |__/    \\______/  \\______/                   \n" +
                    "        /$$$$$$  /$$$$$$$  /$$$$$$$$       /$$$$$$$$ /$$   /$$ /$$$$$$$$ \n" +
                    "       /$$__  $$| $$__  $$| $$_____/      |__  $$__/| $$  | $$| $$_____/ \n" +
                    "      | $$  \\ $$| $$  \\ $$| $$               | $$   | $$  | $$| $$       \n" +
                    "      | $$$$$$$$| $$$$$$$/| $$$$$            | $$   | $$$$$$$$| $$$$$    \n" +
                    "      | $$__  $$| $$__  $$| $$__/            | $$   | $$__  $$| $$__/    \n" +
                    "      | $$  | $$| $$  \\ $$| $$               | $$   | $$  | $$| $$       \n" +
                    "      | $$  | $$| $$  | $$| $$$$$$$$         | $$   | $$  | $$| $$$$$$$$ \n" +
                    "      |__/  |__/|__/  |__/|________/         |__/   |__/  |__/|________/ \n" +
                    "             /$$      /$$ /$$$$$$ /$$   /$$ /$$   /$$ /$$$$$$$$ /$$$$$$$ \n" +
                    "            | $$  /$ | $$|_  $$_/| $$$ | $$| $$$ | $$| $$_____/| $$__  $$\n" +
                    "            | $$ /$$$| $$  | $$  | $$$$| $$| $$$$| $$| $$      | $$  \\ $$\n" +
                    "            | $$/$$ $$ $$  | $$  | $$ $$ $$| $$ $$ $$| $$$$$   | $$$$$$$/\n" +
                    "            | $$$$_  $$$$  | $$  | $$  $$$$| $$  $$$$| $$__/   | $$__  $$\n" +
                    "            | $$$/ \\  $$$  | $$  | $$\\  $$$| $$\\  $$$| $$      | $$  \\ $$\n" +
                    "            | $$/   \\  $$ /$$$$$$| $$ \\  $$| $$ \\  $$| $$$$$$$$| $$  | $$\n" +
                    "            |__/     \\__/|______/|__/  \\__/|__/  \\__/|________/|__/  |__/\n" +
                    "                                                                         \n" +
                    "                                                                         \n" +
                    "                                                                         \n");

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e){
                LOGGER.log(Level.WARNING, "interrupted exception");
                Thread.currentThread().interrupt();
            }

            out.println("\nOMG! CONGRATULATIONS!!! HERE ARE THE POINTS:   ＼(＾O＾)／\n");
            printPoints(leaderBoard);
        }

        else if (isADraw(leaderBoard)){

            out.println("                ██╗    ██╗ ██████╗ ██╗    ██╗          \n" +
                    "                ██║    ██║██╔═══██╗██║    ██║          \n" +
                    "                ██║ █╗ ██║██║   ██║██║ █╗ ██║          \n" +
                    "                ██║███╗██║██║   ██║██║███╗██║          \n" +
                    "                ╚███╔███╔╝╚██████╔╝╚███╔███╔╝          \n" +
                    "                 ╚══╝╚══╝  ╚═════╝  ╚══╝╚══╝           \n" +
                    "            ██████╗ ██████╗  █████╗ ██╗    ██╗██╗██╗██╗\n" +
                    "            ██╔══██╗██╔══██╗██╔══██╗██║    ██║██║██║██║\n" +
                    "            ██║  ██║██████╔╝███████║██║ █╗ ██║██║██║██║\n" +
                    "            ██║  ██║██╔══██╗██╔══██║██║███╗██║╚═╝╚═╝╚═╝\n" +
                    "            ██████╔╝██║  ██║██║  ██║╚███╔███╔╝██╗██╗██╗\n" +
                    "            ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚══╝╚══╝ ╚═╝╚═╝╚═╝\n" +
                    "                                                       \n");

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e){
                LOGGER.log(Level.WARNING, "interrupted exception");
                Thread.currentThread().interrupt();
            }

            out.println("THIS DOES NOT HAPPEN OFTEN  (Ͼ˳Ͽ)..!!!");
            out.println("HERE ARE THE POINTS\n");
            printPoints(leaderBoard);
        }

        else {

            out.println(" ██▓        ▒█████      ▒█████       ██████    ▓█████     ██▀███  \n" +
                    "▓██▒       ▒██▒  ██▒   ▒██▒  ██▒   ▒██    ▒    ▓█   ▀    ▓██ ▒ ██▒\n" +
                    "▒██░       ▒██░  ██▒   ▒██░  ██▒   ░ ▓██▄      ▒███      ▓██ ░▄█ ▒\n" +
                    "▒██░       ▒██   ██░   ▒██   ██░     ▒   ██▒   ▒▓█  ▄    ▒██▀▀█▄  \n" +
                    "░██████▒   ░ ████▓▒░   ░ ████▓▒░   ▒██████▒▒   ░▒████▒   ░██▓ ▒██▒\n" +
                    "░ ▒░▓  ░   ░ ▒░▒░▒░    ░ ▒░▒░▒░    ▒ ▒▓▒ ▒ ░   ░░ ▒░ ░   ░ ▒▓ ░▒▓░\n" +
                    "░ ░ ▒  ░     ░ ▒ ▒░      ░ ▒ ▒░    ░ ░▒  ░ ░    ░ ░  ░     ░▒ ░ ▒░\n" +
                    "  ░ ░      ░ ░ ░ ▒     ░ ░ ░ ▒     ░  ░  ░        ░        ░░   ░ \n" +
                    "    ░  ░       ░ ░         ░ ░           ░        ░  ░      ░     \n" +
                    "                                                                  ");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e){
                LOGGER.log(Level.WARNING, "interrupted exception");
                Thread.currentThread().interrupt();
            }

            out.println("SORRY MY FRIEND, MAYBE NEXT TIME!  ¯\\_(ツ)_/¯");
            out.println("HERE ARE THE POINTS\n");
            printPoints(leaderBoard);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        }
        catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "interrupted exception");
            Thread.currentThread().interrupt();
        }


        out.println("\n\n╔═══════════════════════╗\n" +
                "║        OPTIONS        ║\n" +
                "╠═══════════════════════╣\n" +
                "║ 1. START ANOTHER GAME ║\n" +
                "║ 2. QUIT               ║\n" +
                "╚═══════════════════════╝");
        int choice = readNumbers(1,3);

        if (sameNumbers(choice, 1)){
            clearAttributes();
            displayLoginWindow();
        }
        else if (sameNumbers(choice, 2)){
            System.exit(0);
        }
        else if (sameNumbers(choice, 3)){
            out.println("MMM LOOKS LIKE YOU HAVE UNLOCKED OUR EASTER EGG!!!\n\n\n");
            try {
                TimeUnit.MILLISECONDS.sleep(1400);
                out.println("████████╗ █████╗ ███╗   ██╗                                            \n" +
                        "╚══██╔══╝██╔══██╗████╗  ██║                                            \n" +
                        "   ██║   ███████║██╔██╗ ██║                                            \n" +
                        "   ██║   ██╔══██║██║╚██╗██║                                            \n" +
                        "   ██║   ██║  ██║██║ ╚████║                                            \n" +
                        "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝                                            ");
                TimeUnit.MILLISECONDS.sleep(1400);
                out.println("                    ████████╗ █████╗ ███╗   ██╗                        \n" +
                        "                    ╚══██╔══╝██╔══██╗████╗  ██║                        \n" +
                        "                       ██║   ███████║██╔██╗ ██║                        \n" +
                        "                       ██║   ██╔══██║██║╚██╗██║                        \n" +
                        "                       ██║   ██║  ██║██║ ╚████║                        \n" +
                        "                       ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝                       ");
                TimeUnit.MILLISECONDS.sleep(1400);
                out.println("                                            ████████╗ █████╗ ███╗   ██╗\n" +
                        "                                            ╚══██╔══╝██╔══██╗████╗  ██║\n" +
                        "                                               ██║   ███████║██╔██╗ ██║\n" +
                        "                                               ██║   ██╔══██║██║╚██╗██║\n" +
                        "                                               ██║   ██║  ██║██║ ╚████║\n" +
                        "                                               ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝\n" +
                        "                                                                       ");

                TimeUnit.MILLISECONDS.sleep(1300);
                out.println("██╗   ██╗ ██████╗ ██╗   ██╗██████╗                                                                 \n" +
                        "╚██╗ ██╔╝██╔═══██╗██║   ██║██╔══██╗                                                                \n" +
                        " ╚████╔╝ ██║   ██║██║   ██║██████╔╝                                                                \n" +
                        "  ╚██╔╝  ██║   ██║██║   ██║██╔══██╗                                                                \n" +
                        "   ██║   ╚██████╔╝╚██████╔╝██║  ██║                                                                \n" +
                        "   ╚═╝    ╚═════╝  ╚═════╝ ╚═╝  ╚═╝                                                                \n" +
                        "██████╗ ██████╗  ██████╗  ██████╗ ██████╗  █████╗ ███╗   ███╗███╗   ███╗███████╗██████╗ ███████╗   \n" +
                        "██╔══██╗██╔══██╗██╔═══██╗██╔════╝ ██╔══██╗██╔══██╗████╗ ████║████╗ ████║██╔════╝██╔══██╗██╔════╝██╗\n" +
                        "██████╔╝██████╔╝██║   ██║██║  ███╗██████╔╝███████║██╔████╔██║██╔████╔██║█████╗  ██████╔╝███████╗╚═╝\n" +
                        "██╔═══╝ ██╔══██╗██║   ██║██║   ██║██╔══██╗██╔══██║██║╚██╔╝██║██║╚██╔╝██║██╔══╝  ██╔══██╗╚════██║██╗\n" +
                        "██║     ██║  ██║╚██████╔╝╚██████╔╝██║  ██║██║  ██║██║ ╚═╝ ██║██║ ╚═╝ ██║███████╗██║  ██║███████║╚═╝\n" +
                        "╚═╝     ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝   \n" +
                        "                                                                                                  ");
                TimeUnit.MILLISECONDS.sleep(1300);
                out.println("\n██████╗ ██╗███████╗████████╗██████╗  ██████╗     ██╗     ███████╗███╗   ██╗████████╗██╗███╗   ██╗██╗\n" +
                        "██╔══██╗██║██╔════╝╚══██╔══╝██╔══██╗██╔═══██╗    ██║     ██╔════╝████╗  ██║╚══██╔══╝██║████╗  ██║██║\n" +
                        "██████╔╝██║█████╗     ██║   ██████╔╝██║   ██║    ██║     █████╗  ██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║\n" +
                        "██╔═══╝ ██║██╔══╝     ██║   ██╔══██╗██║   ██║    ██║     ██╔══╝  ██║╚██╗██║   ██║   ██║██║╚██╗██║██║\n" +
                        "██║     ██║███████╗   ██║   ██║  ██║╚██████╔╝    ███████╗███████╗██║ ╚████║   ██║   ██║██║ ╚████║██║\n" +
                        "╚═╝     ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝     ╚══════╝╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝╚═╝\n" +
                        "                                                                                                    ");
                TimeUnit.MILLISECONDS.sleep(1300);
                out.println("\n\n███╗   ███╗██╗ ██████╗██╗  ██╗███████╗██╗     ███████╗    ███╗   ███╗ █████╗ ██████╗  █████╗ ███████╗███████╗██╗\n" +
                        "████╗ ████║██║██╔════╝██║  ██║██╔════╝██║     ██╔════╝    ████╗ ████║██╔══██╗██╔══██╗██╔══██╗╚══███╔╝╚══███╔╝██║\n" +
                        "██╔████╔██║██║██║     ███████║█████╗  ██║     █████╗      ██╔████╔██║███████║██████╔╝███████║  ███╔╝   ███╔╝ ██║\n" +
                        "██║╚██╔╝██║██║██║     ██╔══██║██╔══╝  ██║     ██╔══╝      ██║╚██╔╝██║██╔══██║██╔══██╗██╔══██║ ███╔╝   ███╔╝  ██║\n" +
                        "██║ ╚═╝ ██║██║╚██████╗██║  ██║███████╗███████╗███████╗    ██║ ╚═╝ ██║██║  ██║██║  ██║██║  ██║███████╗███████╗██║\n" +
                        "╚═╝     ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝\n" +
                        "                                                                                                                ");

                TimeUnit.MILLISECONDS.sleep(1300);
                out.println("\n\n███╗   ███╗ █████╗ ██████╗  ██████╗ ██████╗     ███╗   ███╗ █████╗ ██████╗ ██╗███╗   ██╗██╗\n" +
                        "████╗ ████║██╔══██╗██╔══██╗██╔════╝██╔═══██╗    ████╗ ████║██╔══██╗██╔══██╗██║████╗  ██║██║\n" +
                        "██╔████╔██║███████║██████╔╝██║     ██║   ██║    ██╔████╔██║███████║██████╔╝██║██╔██╗ ██║██║\n" +
                        "██║╚██╔╝██║██╔══██║██╔══██╗██║     ██║   ██║    ██║╚██╔╝██║██╔══██║██╔══██╗██║██║╚██╗██║██║\n" +
                        "██║ ╚═╝ ██║██║  ██║██║  ██║╚██████╗╚██████╔╝    ██║ ╚═╝ ██║██║  ██║██║  ██║██║██║ ╚████║██║\n" +
                        "╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝\n" +
                        "                                                                                           ");

                TimeUnit.MILLISECONDS.sleep(2300);
                System.exit(0);

            }
            catch (InterruptedException e){
                LOGGER.log(Level.WARNING, "interrupted exception");
                Thread.currentThread().interrupt();
            }

        }

    }

    /**
     * prints the points of every player
     * @param leaderBoard message with points
     */
    private void printPoints(LeaderBoard leaderBoard){

        out.println("LEADER BOARD:\n");
        for (Character character: leaderBoard.getPointsMap().keySet()){
            printCharacterName(character);
            out.println(" got " + leaderBoard.getPointsMap().get(character) + " points." );
        }
    }

    /**
     * this method check if there was a draw
     * @param leaderBoard message received
     * @return true if there was a draw
     */
    private Boolean isADraw(LeaderBoard leaderBoard){

        int firstPlayers = 0;

        for (Character character: leaderBoard.getLeaderBoard().keySet()){

            if (leaderBoard.getLeaderBoard().get(character) == 1){

                firstPlayers++;
                if (firstPlayers > 1){

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * shows a textual message with the action a player has done (used onli in cli)
     * @param actionReports mesage
     */
    public void displayActionReport(ActionReports actionReports){

        damageSession = actionReports.isDamageSession();
        out.print("  ACTION: ");
        printCharacterName(actionReports.getSubject());
        out.print(actionReports.getReport());
        if (actionReports.getReceiver() != null){
            printCharacterName(actionReports.getReceiver());
            out.println("");
        }
        else {
            out.println("");
        }
    }

    /**
     * Shows that there is already a game with that username
     */
    public void displayAlreadyConnectedWindow() {

        out.println("\nMmm, looks like there is a match started with this username, what do you want to do?");
        out.println("╔═════════════════════╗\n" +
                "║       OPTIONS       ║\n" +
                "╠═════════════════════╣\n" +
                "║ 1. JOIN THE MATCH   ║\n" +
                "║ 2. START A NEW GAME ║\n" +
                "╚═════════════════════╝");
        int choice = readNumbers(1, 2);

        if (sameNumbers(choice, 1)) {

            Message reconnect = new Message(username);
            reconnect.setTypeOfMessage(TypeOfMessage.RECONNECTION);
            client.send(reconnect);
        }

        else {
            try {

                out.println("\nCHOOSE ANOTHER USERNAME THEN!!\n\n");
                TimeUnit.MILLISECONDS.sleep(1000);
                displayLoginWindow();
            }
            catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "interrupted exception");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * shows the return to login window because a player has been disconnected
     */
    public void displayDisconnectionDuringSetup(){

        out.println("\nOOOPS, FIRST PLAYER JUST DISCONNECTED, PLEASE, START A NEW GAME!!!\n\n");
        try {
            firstMatch = false;
            TimeUnit.MILLISECONDS.sleep(1300);
            displayLoginWindow();
        }
        catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "interrupted exception");
            Thread.currentThread().interrupt();
        }

        displayLoginWindow();
    }

    /**
     * display a connection error
     * @param mesToResend resend last message
     */
    public void displayConnectionErrorClient(Message mesToResend){

        out.println("\nWE CAN'T REACH THE SERVER! CHECK YOUR CONNECTION PLEASE! :( \nPRESS SOMETHING TO TRY AGAIN:");
        in.next();
        out.println("\n\n. . .\n\n. . .\n\n. . .\n\n");
        if (mesToResend.getTypeOfMessage() == TypeOfMessage.LOGIN_REPORT) {
            client.connect(mesToResend);
        }
    }

    /**
     * display a connection failure
     */
    public void displayConnectionFailure(){
        out.println("\nCONNECTION FAILURE... PLEASE LOGIN AGAIN WITH SAME USERNAME!!! (your name was: " + username +" \n\n\n");
        clearAttributes();
        displayLoginWindow();
    }
}
