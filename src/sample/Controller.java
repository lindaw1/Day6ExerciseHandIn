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


    @FXML
    void btnEditAction(ActionEvent event) {
    //  Integer agent = cbChooseAgent.getValue();
      //txtAgentFirstName.setText(agent).;

      //Agent a = tvAgentList.getSelectionModel().getSelectedItem();
      //txtAgentFirstName.setText(a.getAgtFirstName());
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
    }

    @FXML
    void cbChooseAgentAction(ActionEvent event) {
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

/*
    public void loadAgentInfo() {
        ObservableList<Agent> agentList = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");

            while (rs.next()) {
                agentList.add(new Agent(
                        rs.getInt("AgentId"),
                        rs.getString("AgtFirstName"),
                        rs.getString("AgtMiddleInitial"),
                        rs.getString("AgtLastName"),
                        rs.getString("AgtBusPhone"),
                        rs.getString("AgtEmail"),
                        rs.getString("AgtPosition"),
                        rs.getInt("AgencyId")
                ));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/

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

/*
                agentName.add( new Agent(
                        rs.getInt("AgentId"),
                        rs.getString("AgtFirstName"),
                        rs.getString("AgtMiddleInitial"),
                        rs.getString("AgtLastName"),
                        rs.getString("AgtBusPhone"),
                        rs.getString("AgtEmail"),
                        rs.getString("AgtPosition"),
                        rs.getInt("AgencyId")));
*/
