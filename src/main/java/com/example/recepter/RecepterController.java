package com.example.recepter;

import com.bdd.ClientManager;
import com.bdd.FactureManager;
import com.bdd.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Client;
import models.Service;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import tools.Generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

public class RecepterController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField nameService;

    @FXML
    private TextField price;

    @FXML
    private ListView serviceList;

    @FXML
    private ListView serviceListOnFacture;

    @FXML
    private Button addService;

    @FXML
    private Button addClients;

    @FXML
    private TextField brand;

    @FXML
    private TextField siret;

    @FXML
    private TextField mail;

    @FXML
    private TextArea adress;

    @FXML
    private ListView clientList;
    private ObservableList<Service> items = FXCollections.observableArrayList();
    private ObservableList<Client> itemsClients = FXCollections.observableArrayList();
    private ObservableList<Service> itemsServiceFacture = FXCollections.observableArrayList();


    @FXML
    private TextField brandE;

    @FXML
    private TextField siretE;

    @FXML
    private TextField emailE;

    @FXML
    private TextArea adresseE;

    @FXML
    private ChoiceBox clientChoice;

    @FXML
    private ChoiceBox serviceChoice;

    private boolean checkFieldOnRecept() {
        if (this.serviceChoice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur manque le service");
            alert.showAndWait();
        }
        if (this.clientChoice.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur manque le client");
            alert.showAndWait();
        }
        if (!this.clientChoice.getSelectionModel().isEmpty() && !this.serviceChoice.getSelectionModel().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void initialize() {
        serviceList.setItems(items);
        clientList.setItems(itemsClients);
        loadServices();
        loadClient();
    }

    public void loadServiceFacture() {
        this.serviceListOnFacture.setItems(this.itemsServiceFacture);
    }

    @FXML
    private void addService(ActionEvent event) {
        String serviceName = this.nameService.getText();
        String priceInput = this.price.getText();

        if (serviceName.isEmpty() || priceInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "information manquante", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                double price = Double.parseDouble(priceInput);
                ServiceManager sm = new ServiceManager();
                if (sm.addService(serviceName, price)) {
                    JOptionPane.showMessageDialog(null, "problème avec base de données", "Erreur bdd", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Ajout avec succès", "sucess", JOptionPane.INFORMATION_MESSAGE);
                    this.loadServices();
                }
            } catch (NumberFormatException e) {
                System.out.println("Le prix doit être un nombre valide");
            }
        }
    }

    private void loadServices() {
        this.items.clear();
        try {
            ServiceManager sm = new ServiceManager();
            ResultSet rs = sm.getServices();

            while (rs.next()) {
                String name = rs.getString("name");
                Double amount = rs.getDouble("amount");
                int id = 0;
                Service service = new Service(id, name, amount);
                this.items.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addClients(ActionEvent event) {
        String brandText = this.brand.getText();
        String siretText = this.siret.getText();
        String mailText = this.mail.getText();
        String adressText = this.adress.getText();
        if (
                brandText.isEmpty() ||
                        siretText.isEmpty() ||
                        mailText.isEmpty() ||
                        adressText.isEmpty()
        ) {
            JOptionPane.showMessageDialog(null, "information manquante", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            ClientManager cm = new ClientManager();
            if (cm.addClients(brandText, siretText, mailText, adressText)) {
                JOptionPane.showMessageDialog(null, "problème avec base de données", "Erreur bdd", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Ajout avec succès", "sucess", JOptionPane.INFORMATION_MESSAGE);
                this.loadClient();
            }
        }
    }

    private void loadClient() {
        this.itemsClients.clear();
        ClientManager cm = new ClientManager();
        ResultSet rs = cm.getClients();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                String siret = rs.getString("siret");
                String mail = rs.getString("mail");
                String adress = rs.getString("adress");
                Client client = new Client(id, brand, siret, mail, adress);
                this.itemsClients.add(client);
            }
        } catch (SQLException e) {

        }
    }

    @FXML
    public void add_info_entreprise(ActionEvent event) {
        // Récupérer les valeurs des champs
        String brand = brandE.getText();
        String siret = siretE.getText();
        String adresse = adresseE.getText();
        String email = emailE.getText();

        // Nouvelle entrée à ajouter (sans infosSupplementaires)
        Map<String, Object> newEntry = new HashMap<>();
        newEntry.put("brand", brand);
        newEntry.put("siret", siret);
        newEntry.put("adresse", adresse);
        newEntry.put("email", email);

        // Spécifier le chemin du fichier JSON
        String filePath = "src/main/resources/json/informations_entreprise.json";

        try {
            // Créer un ObjectMapper pour gérer le JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Créer une liste contenant uniquement la nouvelle entrée
            List<Map<String, Object>> dataList = new ArrayList<>();
            dataList.add(newEntry);

            // Sauvegarder la nouvelle liste dans le fichier JSON, en remplaçant son contenu
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataList);

            JOptionPane.showMessageDialog(null, "Informations ajoutées", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Impossible d'ajouter les informations", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void tabFactureChanged(Event e) {
        System.out.println("changed");
        this.clientChoice.setItems(this.itemsClients);
        this.serviceChoice.setItems(this.items);
    }

    public void addServiceOnRecept(ActionEvent e) {
        if (this.checkFieldOnRecept()) {
            Service serviceText = (Service) this.serviceChoice.getValue();
            Client clientText = (Client) this.clientChoice.getValue();
            this.itemsServiceFacture.add(serviceText);
            System.out.println(itemsServiceFacture);
            this.loadServiceFacture();
        }
    }

    public void generatePdf() {
        Double amount = Generator.SumAmount(this.itemsServiceFacture);
        System.out.println(amount);
        FactureManager fm = new FactureManager();
    }

    public void deletedClients(ActionEvent event) {
        Client selectedClient = (Client) clientList.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            int clientId = selectedClient.getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ClientManager cm = new ClientManager();
                    boolean success = cm.deleteClientById(clientId);

                    if (success) {
                        // Mettre à jour l'affichage en rechargeant la liste des clients
                        this.loadClient();
                        JOptionPane.showMessageDialog(null, "Client supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du client", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        } else {
            // Si aucun client n'est sélectionné, afficher un message d'erreur
            JOptionPane.showMessageDialog(null, "Aucun client sélectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deletedService(ActionEvent event) {
            Service selectedService = (Service) serviceList.getSelectionModel().getSelectedItem();

            if (selectedService != null) {
                int serviceId = selectedService.getId();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce service ?");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        ServiceManager sm = new ServiceManager();
                        boolean success = sm.deleteServiceById(serviceId);

                        if (success) {
                            this.loadServices();
                            JOptionPane.showMessageDialog(null, "Service supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du service", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            } else {
                JOptionPane.showMessageDialog(null, "Aucun service sélectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



