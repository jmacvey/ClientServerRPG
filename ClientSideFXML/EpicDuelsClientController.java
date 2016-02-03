/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSideFXML;

//------------------------------------------------------------------------------
// Custom Game Imports
//------------------------------------------------------------------------------
import ActionProcessor.ActionPackage;
import ActionProcessor.MessageID;
import Context.ClientContext;
import Context.Context;
import Context.TwoPlayerClientContext;
import IO.ClientID;
import IO.IOMSG;
import LogicProcessor.LogicProcessor;
import Team.Team;
import Team.TeamID;
import epicduelsfxml.ActionGridController;
import epicduelsfxml.FXMLDialogController;
import epicduelsfxml.FXMLEpicDuelsController;
import epicduelsfxml.GroupGridController;
import epicduelsfxml.LabelUpdater;
import epicduelsfxml.MapGridController;
import epicduelsfxml.TeamBoxController;
import framework.ADAction;
import framework.ActionID;
import java.io.IOException;
import java.io.Serializable;

//------------------------------------------------------------------------------
// FXML Specific Imports
//------------------------------------------------------------------------------
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jmacvey
 */
public class EpicDuelsClientController extends FXMLEpicDuelsController
        implements Initializable
{
    //--------------------------------------------------------------------------
    // Super class overrides
    //--------------------------------------------------------------------------

    @Override
    public void handleData(Serializable data)
    {
        TwoPlayerClientContext cText = (TwoPlayerClientContext) data;
        System.out.println("Client received IO : "
                + cText.getIOMSG().toString());
        System.out.println("Actions Received: "
                + Integer.toString(((TwoPlayerClientContext) cText).getCurrentActions().size()));
        if (serverMessage != null)
        {
            serverMessage.setText(cText.getMessage());
        }
        switch (cText.getIOMSG())
        {
            case UPDATE_INITIAL_CONTEXT:
                setContext(cText);
                cText.initializeAvailableActions();
                cText.updateContext();
                contextInitialize();
                break;
            case RELAY_CONTEXT:
            case ROLL:
            case MOVE:
                updateContext(cText);
                break;
        }
    }

    @Override
    public void transmitData(IOMSG msg) throws Exception
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        TwoPlayerClientContext copy = tPCC;
        copy.setIOMSG(msg);
        System.out.println("Sending actions: "
                + Integer.toString(
                        ((TwoPlayerClientContext) copy).getCurrentActions().size()));
        System.out.println("Sending Message : " + copy.getIOMSG());
        getConnection().send(copy);
    }

    @Override
    public void updateContext(Context cText)
    {
        TwoPlayerClientContext tPCC = (TwoPlayerClientContext) cText;
        System.out.println("updating context...");
        setContext(tPCC);
        tPCC.updateContext();
        tPCC.initializeAvailableActions();
        updatePassiveFields();
        labelUpdater.updateLabels();
        mapGridController.drawGameMap();
        mapGridController.updateMovesLabel(tPCC.getDie().getDistance());
        setButtonsDisabled();
        try
        {
            setButtonHandlers();
        } catch (Exception ex)
        {
            Logger.getLogger(EpicDuelsClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates all public, passive fields for the players.
     */
    private void updatePassiveFields()
    {
        TwoPlayerClientContext tPCC = (TwoPlayerClientContext) getContext();
        actionRemValue.setText(Integer.toString(tPCC.getActionNum()));
        actionRemValue.setFill(tPCC.getActionNum() == 0 ? Paint.valueOf("ff0000")
                : Paint.valueOf("32cd32"));
        rollValue.setText(tPCC.getDie().toString());
        rollValue.setFill(tPCC.getDie().getPaint());
    }

    public void updateGroups()
    {
    }

    public boolean getRollDisabled()
    {
        return rollButton.isDisabled();
    }

    public boolean getDrawDisabled()
    {
        return drawButton.isDisabled();
    }

    public void setButtonsDisabled()
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();

        drawButton.setDisable(tPCC.getButtonDisabled().get(0));
        rollButton.setDisable(tPCC.getButtonDisabled().get(1));
    }

    /**
     *
     *
     * @author Jmacvey
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    protected void defend(ActionEvent event) throws IOException
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        FXMLLoader dialogFXML = new FXMLLoader();
        dialogFXML.setLocation(getClass().getResource("../FXMLViews/Dialog.fxml"));
        dialogFXML.load();
        Parent root = dialogFXML.getRoot();
        Stage modal_dialog = new Stage(StageStyle.DECORATED);
        modal_dialog.initModality(Modality.WINDOW_MODAL);
        modal_dialog.initOwner(getStage());
        Scene scene = new Scene(root);
        modal_dialog.getIcons().setAll(getStage().getIcons());
        FXMLDialogController dialogController
                = (FXMLDialogController) dialogFXML.getController();
        dialogController.setStage(modal_dialog);
        dialogController.setButtons(tPCC.getActionButtons());
        dialogController.setLabel(tPCC.getCurrentLabel());
        modal_dialog.setScene(scene);
        modal_dialog.showAndWait();
    }

    //--------------------------------------------------------------------------
    // Initialization Methods
    //--------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {}

    public void manualInit() throws Exception
    {
        try
        {
            initializeContext(true);
        } catch (Exception ex)
        {
            Logger.getLogger(EpicDuelsClientController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void contextInitialize()
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        lp = new LogicProcessor(
                // tPCC, 
                this, actionGridController,
                actionGridTwoController,
                mapGridController);
        initializeControllers();
        initializeTeamBoxes();
        labelUpdater = new LabelUpdater(//tPCC, 
                this, mapGridController,
                actionGridController,
                actionGridTwoController,
                aGGridController,
                sGGridController, lp);
        try
        {
            setButtonHandlers();
        } catch (Exception ex)
        {
            Logger.getLogger(FXMLEpicDuelsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        actionRemValue.setText(Integer.toString(tPCC.getActionNum()));
        drawButton.setDisable(true);
        mapGridController.drawGameMap();
    }

    /**
     * Getter method for the logic processor used by nested controllers.
     *
     * @return the logic processor.
     */
    public LogicProcessor getLogicProcessor()
    {
        return this.lp;
    }

    /**
     * Getter method for the label updater used by nested controllers.
     *
     * @return the label updater
     */
    public LabelUpdater getLabelUpdater()
    {
        return this.labelUpdater;
    }

    //--------------------------------------------------------------------------
    // Utility Methods : GUI
    //--------------------------------------------------------------------------
    public void updateTeams()
    {
        teamBoxOneController.updateTeamData();
        teamBoxTwoController.updateTeamData();
        teamBoxThreeController.updateTeamData();
        teamBoxFourController.updateTeamData();
    }

    /**
     * Sets the drawButton as disabled or enabled.
     *
     * @param disable the disable flag
     */
    public void setDrawButtonDisable(boolean disable)
    {
        drawButton.setDisable(disable);
    }

    /**
     * Sets the roll button as disabled or enabled
     *
     * @param disable the disable flag
     */
    public void setRollButtonDisable(boolean disable)
    {
        rollButton.setDisable(disable);
    }

    public void setActionRemValue(String value, Paint color)
    {
        actionRemValue.setText(value);
        actionRemValue.setFill(color);
    }

    private void decUpdateActionNum()
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        tPCC.setActionNum(tPCC.getActionNum() - 1);
        actionRemValue.setText(Integer.toString(tPCC.getActionNum()));
        actionRemValue.setFill(tPCC.getActionNum() == 0
                ? Paint.valueOf("ff0000") : Paint.valueOf("32cd32"));
    }

    /**
     * Pops up an information dialog containing a message and title
     *
     * @param message the message
     * @param title the title
     */
    public void informUser(String message, String title)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Disables all buttons on the stage.
     */
    public void disableAllButtons()
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        tPCC.getTeamOneActionButtons().stream().forEach((b) ->
        {
            b.setDisable(true);
        });
        tPCC.getTeamTwoActionButtons().stream().forEach((b) ->
        {
            b.setDisable(true);
        });
        drawButton.setDisable(true);
    }

    /**
     * Fires the defense button for use in case of attack.
     */
    public void fireDefend()
    {
        defendButton.fire();
    }

    /**
     * Sets handlers for the draw and roll buttons
     */
    private void setButtonHandlers() throws Exception
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) this.getContext();

        rollButton.setOnAction(e ->
        {
            System.out.println("Client clicked on roll button!");
            tPCC.getDie().roll();
            rollValue.setText(tPCC.getDie().toString());
            rollValue.setFill(tPCC.getDie().getPaint());
            rollButton.setDisable(true);
            tPCC.setActionNum(2);
            mapGridController.storeMovesLabel(tPCC.getDie().getDistance(),
                    tPCC.getSelectedActor(),
                    tPCC.getBoardManager().getCurrentTeam().getTeamID(),
                    tPCC.getBoardManager().getCurrentTeam().getPlayerOnTeam(
                            tPCC.getSelectedActor()).getPosition());

            drawButton.setDisable(false);
            tPCC.updateDrawRollDisable(false, true);
            tPCC.setIsMoving(true);
            tPCC.setMessage("You rolled a "
                    + tPCC.getDie().toString() + "!");
            try
            {
                transmitData(IOMSG.ROLL);
            } catch (Exception ex)
            {
                Logger.getLogger(EpicDuelsClientController.class.
                        getName()).log(Level.SEVERE, null, ex);
            }
        });
        drawButton.setOnAction(e ->
        {
            System.out.println("Client clicked on draw button!");
            // get the teamID
            TeamID id = tPCC.
                    getBoardManager().getCurrentTeam().getTeamID();
            // clear passive fields
            tPCC.setIsMoving(false);
            tPCC.getMoveableSpaces().clear();
            tPCC.setMessage("You" + " drew a card. ");
            this.setContext(tPCC);
            // create the action package
            ActionPackage ap = new ActionPackage(new ADAction(null, ActionID.DRAW, 0, 0), id,
                    tPCC.getSelectedActor(),
                    null, null, MessageID.DRAW, 0, null, 0);
            rollButton.setDisable(true);
            try
            {
                lp.interpretAction(ap, null, false);
            } catch (Exception ex)
            {
                Logger.getLogger(FXMLEpicDuelsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            

//            drawButton.setDisable(tPCC.getActionNum() == 0
//                    || tPCC.getActionNum() == -1);
//            mapGridController.drawGameMap();
        });
    }

    private void initializeControllers()
    {
        // map grid
//        mapGridController.setContext(tPCC);
        mapGridController.setMainController(this);
        // action grid
//        actionGridController.setContext(tPCC);
        actionGridController.setMainController(this);
        actionGridTwoController.setMainController(this);
        // team boxes
        initializeTeamBoxes();
        initializeGroups();
    }

    private void initializeTeamBoxes()
    {
        TwoPlayerClientContext tPCC
                = (TwoPlayerClientContext) getContext();
        //teamBoxOneController.setContext(tPCC);
        teamBoxOneController.setMainController(this);
        //teamBoxTwoController.setContext(tPCC);
        teamBoxTwoController.setMainController(this);
        //teamBoxThreeController.setContext(tPCC);
        teamBoxThreeController.setMainController(this);
        //teamBoxFourController.setContext(tPCC);
        teamBoxFourController.setMainController(this);
//        sGGridController.setContext(tPCC);
        sGGridController.setMainController(this);

        // get the team List
        List<Team> teamList = tPCC.getAllTeams();
        teamBoxOneController.initializeBox(teamList.get(0));
        teamBoxTwoController.initializeBox(teamList.get(1));
        teamBoxThreeController.initializeBox(teamList.get(2));
        teamBoxFourController.initializeBox(teamList.get(3));
    }

    private void initializeGroups()
    {
        // sGGridController.setContext(tPCC);
        sGGridController.setMainController(this);
        sGGridController.setGroupLabel("Can Attack");
        // aGGridController.setContext(tPCC);
        aGGridController.setMainController(this);
        aGGridController.setGroupLabel("In Play");
    }

    /**
     * Prompts the server for the context in the game.
     *
     * @param isTwoPlayerGame if the game is two player
     */
    private void initializeContext(boolean isTwoPlayerGame)
    {
        ClientContext cText = new TwoPlayerClientContext(
                IOMSG.QUERY_INITIAL_CONTEXT);
        try
        {
            getConnection().send(cText);
        } catch (Exception ex)
        {
            Logger.getLogger(EpicDuelsClientController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    //--------------------------------------------------------------------------
    // FXML fields: Nested Controllers and Included FXML objects
    //--------------------------------------------------------------------------
    @FXML
    private Text actionRemValue;
    @FXML
    private Text rollValue;

    @FXML
    private VBox sGGrid;
    @FXML
    private GroupGridController sGGridController;
    @FXML
    private VBox aGGrid;
    @FXML
    private GroupGridController aGGridController;

    // buttons and values
    @FXML
    private Button rollButton;
    @FXML
    private Button drawButton;
    @FXML
    private Button defendButton;

    // Team Boxes
    @FXML
    private VBox teamBoxOne;
    @FXML
    private TeamBoxController teamBoxOneController;
    @FXML
    private VBox teamBoxTwo;
    @FXML
    private TeamBoxController teamBoxTwoController;
    @FXML
    private VBox teamBoxThree;
    @FXML
    private TeamBoxController teamBoxThreeController;
    @FXML
    private VBox teamBoxFour;
    @FXML
    private TeamBoxController teamBoxFourController;

    @FXML
    private VBox mapGrid;
    @FXML
    private MapGridController mapGridController;

    @FXML
    private GridPane actionGrid;
    @FXML
    private ActionGridController actionGridController;
    @FXML
    private GridPane actionGridTwo;
    @FXML
    private ActionGridController actionGridTwoController;
    @FXML
    private Label serverMessage;

    //--------------------------------------------------------------------------
    // private data fields
    //--------------------------------------------------------------------------
    // the processor for the logic
    private LogicProcessor lp;
    // updates all the labels on the field
    private LabelUpdater labelUpdater;

    private ClientID clientID;

}
