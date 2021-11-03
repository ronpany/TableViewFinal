/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableviewfinal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author RONNY PANTOJA
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private TableColumn<Persona, Integer> id;

@FXML
    private Button Btn_Actualizar;

    @FXML
    private Button Btn_Agregar;

    @FXML
    private Button Btn_Eliminar;

    @FXML
    private TextField Txt_Address;

    @FXML
    private TextField Txt_CC;

    @FXML
    private TextField Txt_Cellphone;

    @FXML
    private TextField Txt_Email;

    @FXML
    private TextField Txt_Name;

    @FXML
    private TableColumn<Persona, String> col_address;

    @FXML
    private TableColumn<Persona, String> col_cellphone;

    @FXML
    private TableColumn<Persona, String> col_email;

    @FXML
    private TableColumn<Persona, String> col_name;

    @FXML
    private TableView<Persona> table_person;
    
    ObservableList<Persona> list = FXCollections.observableArrayList(
    new Persona("10","Messi","messientofeliz@gmai.com","Paris","30"));   
     
    // Este metodo nos valida si los TxtFields de la interfaz principal estan completamente llenos o no y nos retorna
    // una respuesta (true or false)
    
    public Boolean validateDates(){
        Boolean band = false;
        if(Txt_CC.getText().equals("")||Txt_Email.getText().equals("")||Txt_Address.getText().equals("")||Txt_Name.getText().equals("")||Txt_Cellphone.getText().equals("")){           
        }else{band=true;}
    return band;
    }
    
    // Este metodo nos limpea los TxtField
    public void clearData(){
        Txt_Address.setText(null);
        Txt_CC.setText(null);
        Txt_Cellphone.setText(null);
        Txt_Email.setText(null);
        Txt_Name.setText(null);
    }
    // Este metodo lleva un parametro el cual es la cedula de la persona, y nos busca el la lista
    // Si se encuentra o no dicho objeto
    
    public Boolean searchPerson(String id){
        Boolean band = false;
        for (Persona p:list){
            if(p.getId().equals(id)){
                band = true;
            }
        }
    return band;
    }
    // Este metodo incia todo lo que hayamos programado, y recibe como parametro la ruta del documento FXML que se diseño
    // en SceneBuilder
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Btn_Agregar.getStyleClass().add("redbutton");
    
    id.setCellValueFactory(new PropertyValueFactory<Persona,Integer>("id"));        
    col_email.setCellValueFactory(new PropertyValueFactory<Persona,String>("Email"));
    col_address.setCellValueFactory(new PropertyValueFactory<Persona,String>("Address"));
    col_cellphone.setCellValueFactory(new PropertyValueFactory<Persona,String>("Cellphone"));
    col_name.setCellValueFactory(new PropertyValueFactory<Persona,String>("Name"));
    
    table_person.setItems(list);
    table_person.setEditable(false);
    
    // Manejo de Eventos--------------------------------------------
    
    // En este apartado le asignamos un metodo a la tabla, con el fin de que al seleccionar un registro, este automaticamente
    // los imprima en los TextFields creados
    table_person.setOnMouseClicked(event -> {
        try {
          Persona p = table_person.getSelectionModel().getSelectedItem();
          Txt_CC.setText(String.valueOf(p.getId()));
          Txt_Name.setText(p.getName());
          Txt_Address.setText(p.getAddress());
          Txt_Email.setText(p.getEmail());
          Txt_Cellphone.setText(p.getCellphone());  
        } catch (NullPointerException e) {
        }
          
    });
    
    // Evento de AGREGAR un Registro con sus respectivas condiciones
    Btn_Agregar.setOnAction((event) -> {
        try {
         if(validateDates()){
            if(searchPerson(Txt_CC.getText())==false){
                list.add(new Persona(Txt_CC.getText(), Txt_Name.getText(), Txt_Email.getText(), Txt_Address.getText(), Txt_Cellphone.getText()));
            }else{JOptionPane.showMessageDialog(null, "La Persona con CC: "+Txt_CC.getText()+" ya esta REGISTRADA");}
        }
        clearData();   
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar Datos!");
        }
        
        
    });
    
    // Evento de ELIMINAR un registro seleccionado previamente
    Btn_Eliminar.setOnAction((event) -> {
        if(table_person.getSelectionModel().getSelectedIndex()>=0){
        list.remove(table_person.getSelectionModel().getSelectedIndex());
        }else{JOptionPane.showMessageDialog(null, "Debe de haber al menos 1 dato en la tabla!");}
        clearData();
    });
    
    // Evento de ACTUALIZAR un registro seleccionado con sus respectivas condiciones
    Btn_Actualizar.setOnAction((event) -> {        
        if(table_person.getSelectionModel().getSelectedIndex()>=0){
            Persona pv = list.get(table_person.getSelectionModel().getSelectedIndex());
            Persona pn = new Persona(Txt_CC.getText(), Txt_Name.getText(), Txt_Email.getText(), Txt_Address.getText(), Txt_Cellphone.getText());
            if(!pv.getId().equals(pn.getId())){
                if(searchPerson(pn.getId())==false){
                    list.set(table_person.getSelectionModel().getSelectedIndex(), pn);
                }else{JOptionPane.showMessageDialog(null, "No puedes usar la CC: "+pn.getId());}
            }else{list.set(table_person.getSelectionModel().getSelectedIndex(), pn);}
            clearData();
        }else{JOptionPane.showMessageDialog(null, "Debe seleccionar un registro!");}
    });
    }    
    
}
