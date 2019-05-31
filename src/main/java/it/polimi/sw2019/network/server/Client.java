import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;

public class Client {

    /**
     * Constructor
     */
    public Client(String username, ClientInterface clientInterface) {

        setUsername(username);
        setClientInterface(clientInterface);
        setConnected(true);
    }

    /* Attributes */

    private String username;

    private ClientInterface clientInterface;

    private Boolean connected;

    /* Methods */

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

}