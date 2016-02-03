/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSideFXML;

import Context.ServerContext;
import IO.Server;
import IO.ServerNetworkConnection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
public class EpicDuelsFXMLServer extends Application
{

    @Override
    public void init() throws Exception
    {
        serverConnection.startConnection();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("../FXMLViews/ServerView.fxml"));
        fxml.load();
        Parent root = fxml.getRoot();
        mainController
                = (EpicDuelsServerController) fxml.getController();

        mainController.setStage(stage);
        mainController.setConnection(serverConnection);
        // create a new Server context
        mainController.setContext(new ServerContext());
        mainController.setServerContext((ServerContext) mainController.getContext());
        mainController.addToMessageQueue(serverConnection.getStatus());
        Dimension windowD = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(root, windowD.width * .4, windowD.height * .4);
        stage.setTitle("Star Wars Epic Duels Server");
        setIcon(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception
    {
        serverConnection.closeConnection();
    }

    /**
     * Sets the icon for the stage.
     *
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
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    private Server createServer()
    {
        return new Server(55555, data ->
        {
            Platform.runLater(() ->
            {
                    try
                    {
                        mainController.handleData(data);
                    } catch (Exception ex)
                    {
                        Logger.getLogger(EpicDuelsFXMLServer.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
            });
        });
    }

    private EpicDuelsServerController mainController;
    private ServerNetworkConnection serverConnection = createServer();

}
