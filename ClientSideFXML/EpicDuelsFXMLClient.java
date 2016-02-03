/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSideFXML;

import Context.ClientContext;

import Context.TwoPlayerClientContext;
import IO.Client;
import IO.ClientNetworkConnection;
import IO.IOMSG;
import ServerSideFXML.EpicDuelsFXMLServer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jmacvey
 */
public class EpicDuelsFXMLClient extends Application
{

    public EpicDuelsFXMLClient() throws UnknownHostException
    {
        this.clientConnection = createClient();
    }

    @Override
    public void init() throws Exception
    {
        clientConnection.startConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
    
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("../FXMLViews/ClientView.fxml"));
        fxml.load();
        Parent root = fxml.getRoot();
        mainController
                = (EpicDuelsClientController) fxml.getController();

        mainController.setStage(stage);
        mainController.setConnection(clientConnection);
        mainController.manualInit();
        // queries the server for the server context
//        mainController.manualInit(true);
        Dimension windowD = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(root, windowD.width * .8, windowD.height * .8);
        stage.setTitle("Star Wars Epic Duels Client");
        setIcon(stage);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the icon for the stage.
     * @param stage the stage.
     */
    private void setIcon(Stage stage)
    {
        URL url = EpicDuelsFXMLServer.class.getResource("../Images/vaderIcon.jpg");
        if (url != null)
        {
            stage.getIcons().add(new Image(url.toExternalForm()));
        } else
        {
            System.out.println("Error: Null image detected");
        }
    }

    /**
     * Closes the connection.
     * @throws Exception 
     */
    @Override
    public void stop() throws Exception
    {
        ClientContext cText = new TwoPlayerClientContext(IOMSG.REQUEST_STOP);
        System.out.println("REQUESTING SERVER TO CLOSE CONNECTION...");
        clientConnection.send(cText);   
    }

    private Client createClient() throws UnknownHostException
    {
        System.out.println("Created client");
        return new Client(Inet4Address.getLocalHost().getHostAddress(), 55555, data ->
        {
            Platform.runLater(() ->
            {
                mainController.handleData(data);
            });
        });
    }

    private EpicDuelsClientController mainController;
    private ClientNetworkConnection clientConnection;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
