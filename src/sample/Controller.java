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

    ObservableList agentName = FXCollections.observableArrayList();
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbChooseAgent;

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
    private TextField TxtAgentPosition;

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
      //  String agent = cbChooseAgent.getValue();
      // txtAgentFirstName.setText(agent);

      Agent a = tvAgentList.getSelectionModel().getSelectedItem();
      txtAgentFirstName.setText(a.getAgtFirstName());
    }

    @FXML
    void btnSaveAction(ActionEvent event) {

    }

    @FXML
    void cbChooseAgentAction(ActionEvent event) {
        String output = cbChooseAgent.getSelectionModel().getSelectedItem().toString();
        System.out.println(output);

    }

    @FXML
    void initialize() {
        assert cbChooseAgent != null : "fx:id=\"cbChooseAgent\" was not injected: check your FXML file 'sample.fxml'.";
        assert vbLabels != null : "fx:id=\"vbLabels\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentId != null : "fx:id=\"txtAgentId\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentFirstName != null : "fx:id=\"txtAgentFirstName\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentMiddleInitial != null : "fx:id=\"txtAgentMiddleInitial\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentLastName != null : "fx:id=\"txtAgentLastName\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentBusinessPhone != null : "fx:id=\"txtAgentBusinessPhone\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgentEmail != null : "fx:id=\"txtAgentEmail\" was not injected: check your FXML file 'sample.fxml'.";
        assert TxtAgentPosition != null : "fx:id=\"TxtAgentPosition\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtAgencyId != null : "fx:id=\"txtAgencyId\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert tvAgentList != null : "fx:id=\"tvAgentList\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtFirstName != null : "fx:id=\"colAgtFirstName\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtMiddleInitial != null : "fx:id=\"colAgtMiddleInitial\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtLastName != null : "fx:id=\"colAgtLastName\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtBusPhone != null : "fx:id=\"colAgtBusPhone\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtEmail != null : "fx:id=\"colAgtEmail\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgtPosition != null : "fx:id=\"colAgtPosition\" was not injected: check your FXML file 'sample.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'sample.fxml'.";
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
        String query = "SELECT AgtFirstName FROM agents";
        Connection conn = DBHelper.getConnection();

        try {
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while(rs.next()){
                agentName.add(rs.getString("AgtFirstName"));
            }
            pst.close();
            rs.close();

            cbChooseAgent.setItems(agentName);

        } catch (SQLException e) {
            System.out.println("Contact your IT Department");
            e.printStackTrace();
        }
    }


    private void loadAgents() {
        ObservableList<Agent> agentList = FXCollections.observableArrayList();
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
