package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;

import java.io.PrintWriter;
import java.util.*;


public class CLI implements ViewInterface {

    /**
     * Customized constructor
     */
    @SuppressWarnings("squid:S106")
    public CLI(Client client){

        setClient(client);
        out = new PrintWriter(System.out, true);
        in = new Scanner(System.in);
    }

    /**
     * Default constructor
     */
    @SuppressWarnings("squid:S106")
    public CLI(){
        out = new PrintWriter(System.out, true);
        in = new Scanner(System.in);
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

    private Client client;

    private static PrintWriter out;

    private static Scanner in;



    /* Methods */

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

        CLI prova = new CLI();
        prova.getUsernames().add("prova1");
        prova.getUsernames().add("prova2");
        prova.getUsernames().add("prova3");
        prova.getCharacters().add(Character.DISTRUCTOR);
        prova.getCharacters().add(Character.BANSHEE);
        prova.getCharacters().add(Character.VIOLET);
        MatchSetup matchSetup = new MatchSetup(true, false, "Board3.json");
        Message message = new Message("All");
        message.createMessageMatchSetup(matchSetup);
        MatchStart matchStart = new MatchStart(message ,prova.getUsernames(), prova.getCharacters());
        prova.displayMatchStart(matchStart);
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

            out.println("\n(　-_･) ︻デ═一  ▸    " + usernames.get(i) + "  using  " + characters.get(i));
        }

        out.println("\n\nTURN OF THE FIRST PLAYER:\n");
    }

    /**
     * show the window with the possible actions
     * @param answer if answer is false player can't click on shoot action
     */
    public void displayCanIShoot(boolean answer){

        //TODO implement
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
