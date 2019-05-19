//Prepared by Linda Wallace
//Day 6 Exercise
//Winter OOSD
//May 20, 2019
//Program uses the fxml from sample.fxml, agent class, DBHelper to create the actual App.

package sample;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Controller {
//global variables
    ObservableList agentIdComboBox = FXCollections.observableArrayList();
    ObservableList<Agent> agentList = FXCollections.observableArrayList();

    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> cbChooseAgent;

    @FXML
    private VBox vbLabels;

    @FXML
    private TextField txtAgentId;

    @FXML
    private TextField txtAgentFirstName;

    @FXML
    private TextField txtAgentMiddleInitial;

    @FXML
    private TextField txtAgentLastName;

    @FXML
    private TextField txtAgentBusinessPhone;

    @FXML
    private TextField txtAgentEmail;

    @FXML
    private TextField txtAgentPosition;

    @FXML
    private TextField txtAgencyId;

    @FXML
    private TableView<Agent> tvAgentList;

    @FXML
    private TableColumn<Agent, Integer> colAgentId;

    @FXML
    private TableColumn<Agent, String> colAgtFirstName;

    @FXML
    private TableColumn<Agent, String> colAgtMiddleInitial;

    @FXML
    private TableColumn<Agent, String> colAgtLastName;

    @FXML
    private TableColumn<Agent, String> colAgtBusPhone;

    @FXML
    private TableColumn<Agent, String> colAgtEmail;

    @FXML
    private TableColumn<Agent, String> colAgtPosition;

    @FXML
    private TableColumn<Agent, Integer> colAgencyId;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    private Main main;

//When the Edit button is clicked it enables most of the text boxes and the Save button. (Not Agent Id text box)
    @FXML
    void btnEditAction(ActionEvent event) {
       // txtAgentId.setEditable(true);
        txtAgentFirstName.setEditable(true);
        txtAgentMiddleInitial.setEditable(true);
        txtAgentLastName.setEditable(true);
        txtAgentEmail.setEditable(true);
        txtAgentPosition.setEditable(true);
        txtAgencyId.setEditable(true);

        btnSave.setDisable(false);
    }

//Save button is supposed to save the modifications of the form.  I was unable to get it to work.
    @FXML
    void btnSaveAction(ActionEvent event) {
        Alert under_construction = new Alert(Alert.AlertType.INFORMATION, "Save Feature is Under Construction", ButtonType.OK);
        under_construction.showAndWait();

        //Connection conn = DBHelper.getConnection();
/*
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE `agents` SET AgtFirstName`=?,`AgtMiddleInitial`=?,`AgtLastName`=?,`AgtBusPhone`=?,`AgtEmail`=?,`AgtPosition`=? WHERE AgentId=?");

            stmt.setString(1, txtAgentFirstName.getText());
            stmt.setString(2, txtAgentMiddleInitial.getText());
            stmt.setString(3, txtAgentLastName.getText());
            stmt.setString(4, txtAgentBusinessPhone.getText());
            stmt.setString(5, txtAgentEmail.getText());
            stmt.setString(6, txtAgentPosition.getText());
            stmt.setString(7, txtAgencyId.getText());
            stmt.setInt(8, cbChooseAgent.getSelectionModel().getSelectedIndex());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error updating the database", ButtonType.OK);
                alert.showAndWait();
            }
            conn.close();
            loadAgents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    }

//uses combobox selection to populate the text fields on the form
    @FXML
    void cbChooseAgentAction(ActionEvent event) {
        btnEdit.setDisable(false);
        int selectedIndex = cbChooseAgent.getSelectionModel().getSelectedIndex();
        Agent agt = agentList.get(selectedIndex);

        txtAgentId.setText(String.valueOf(agt.getAgentId()));
        txtAgentFirstName.setText(agt.getAgtFirstName());
        txtAgentMiddleInitial.setText(agt.getAgtMiddleInitial());
        txtAgentLastName.setText(agt.getAgtBusPhone());
        txtAgentEmail.setText(agt.getAgtEmail());
        txtAgentPosition.setText(agt.getAgtPosition());
        txtAgencyId.setText(String.valueOf(agt.getAgencyId()));
    }

    @FXML
    void initialize() {

        colAgentId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty().asObject());
        colAgtFirstName.setCellValueFactory(cellData -> cellData.getValue().agtFirstNameProperty());
        colAgtMiddleInitial.setCellValueFactory(cellData -> cellData.getValue().agtMiddleInitialProperty());
        colAgtLastName.setCellValueFactory(cellData -> cellData.getValue().agtLastNameProperty());
        colAgtBusPhone.setCellValueFactory(cellData -> cellData.getValue().agtBusPhoneProperty());
        colAgtEmail.setCellValueFactory(cellData -> cellData.getValue().agtEmailProperty());
        colAgtPosition.setCellValueFactory(cellData -> cellData.getValue().agtPositionProperty());
        colAgencyId.setCellValueFactory(cellData -> cellData.getValue().agencyIdProperty().asObject());

        fillComboBox();
        loadAgents();

    }

    //gets info from database to populate the combo box
    public void fillComboBox(){
        String query = "SELECT AgentId FROM agents";
        Connection conn = DBHelper.getConnection();

        try {
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while(rs.next()){
                agentIdComboBox.add(rs.getString("AgentId"));
            }
            pst.close();
            rs.close();

            cbChooseAgent.setItems(agentIdComboBox);

        } catch (SQLException e) {
            System.out.println("Contact your IT Department");
            e.printStackTrace();
        }
    }

//creates the Table view of all the agents
    private void loadAgents() {
        Connection conn = DBHelper.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");
            while (rs.next())
            {
                agentList.add(new Agent(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8)
                ));
            }
            conn.close();
            tvAgentList.setItems(agentList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setMain(Main main) {
        this.main = main;
    }


}


