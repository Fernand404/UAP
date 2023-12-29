package com.example.uapfixed;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LSORegistration extends Application {

    //membuat tabel
    TableView<Mahasiswa> tableView;

    //main
    public static void main(String[] args) {
        launch(args);
    }

    //fungsi untuk menampilkan aplikasi
    @Override
    public void start(Stage primaryStage) {

        //membuat judul aplikasinya
        primaryStage.setTitle("Aplikasi Pendaftaran LSO Robotika");

        //membuat field/ruang untuk memasukkan data
        TextField namaField = new TextField();
        namaField.setPromptText("Nama Lengkap");

        TextField nimField = new TextField();
        nimField.setPromptText("NIM");

        TextField fakultasField = new TextField();
        fakultasField.setPromptText("Fakultas");

        TextField jurusanField = new TextField();
        jurusanField.setPromptText("Jurusan");

        TextField peranField = new TextField();
        peranField.setPromptText("Peran");

        //membuat tombol tambah untuk memasukkan data yang telah dimasukkan ke dalam field ke dalam tabel yang telah disediakan
        Button addButton = new Button("Tambah");
        addButton.setPrefWidth(150); // Mengatur lebar tombol "Tambah"
        addButton.setOnAction(e -> {
            if (!namaField.getText().isEmpty() && !nimField.getText().isEmpty() && !fakultasField.getText().isEmpty() && !jurusanField.getText().isEmpty() && !peranField.getText().isEmpty()) {
                try {
                    Integer.parseInt(nimField.getText());
                    tableView.getItems().add(new Mahasiswa(namaField.getText(), nimField.getText(), fakultasField.getText(), jurusanField.getText(), peranField.getText()));
                    namaField.clear();
                    nimField.clear();
                    fakultasField.clear();
                    jurusanField.clear();
                    peranField.clear();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Input Error!", "NIM harus berupa angka!");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Input Error!", "Semua field harus diisi!");
            }
        });

        //membuat tombol hapus untuk menghapus data yang sudah dimasukkan dan sudah dipilih sebelumnya
        Button deleteButton = new Button("Hapus");
        deleteButton.setPrefWidth(150); // Mengatur lebar tombol "Hapus"
        deleteButton.setOnAction(e -> {
            Mahasiswa selectedMahasiswa = tableView.getSelectionModel().getSelectedItem();
            if (selectedMahasiswa != null) {
                tableView.getItems().remove(selectedMahasiswa);
            }
        });

        //membuat tombol update untuk mengupdate data yang telah dimasukkan dan sudah dipilih sebelumnnya
        Button updateButton = new Button("Perbarui");
        updateButton.setPrefWidth(150);
        updateButton.setOnAction(e -> {
            Mahasiswa selectedMahasiswa = tableView.getSelectionModel().getSelectedItem();
            if (selectedMahasiswa != null) {
                showUpdatePopup(selectedMahasiswa);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error!", "Pilih mahasiswa yang akan diperbarui!");
            }
        });

        //membuat tombol simpan untuk menyimpan data yang sudah dimasukkan dan dipilih sebelumnya ke dalam bentuk txt
        Button saveButton = new Button("Simpan");
        saveButton.setPrefWidth(150); // Mengatur lebar tombol "Simpan"
        saveButton.setOnAction(e -> {
            try {
                saveData(tableView.getItems());
                showAlert(Alert.AlertType.INFORMATION, "Selesai", "Data berhasil disimpan!");
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "404", "Terjadi kesalahan saat menyimpan data!");
            }
        });

        //membuat kolom header
        TableColumn<Mahasiswa, String> namaColumn = new TableColumn<>("Nama");
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Mahasiswa, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<Mahasiswa, String> fakultasColumn = new TableColumn<>("Fakultas");
        fakultasColumn.setCellValueFactory(new PropertyValueFactory<>("fakultas"));

        TableColumn<Mahasiswa, String> jurusanColumn = new TableColumn<>("Jurusan");
        jurusanColumn.setCellValueFactory(new PropertyValueFactory<>("jurusan"));

        TableColumn<Mahasiswa, String> peranColumn = new TableColumn<>("Peran");
        peranColumn.setCellValueFactory(new PropertyValueFactory<>("peran"));

        //membuat tabel baru
        tableView = new TableView<>();

        //membuat kolom untuk menambahkan data yang sudah dimasukkan ke dalam field ke dalam tabel yang telah disediakan
        tableView.getColumns().add(namaColumn);
        tableView.getColumns().add(nimColumn);
        tableView.getColumns().add(fakultasColumn);
        tableView.getColumns().add(jurusanColumn);
        tableView.getColumns().add(peranColumn);

        //membuat horizontal box untuk menampung field/ruang dan tombol yang sebelumnya sudah dibuat
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(20));
        hBox.getChildren().addAll(namaField, nimField, fakultasField, jurusanField, peranField, addButton, deleteButton, updateButton, saveButton);

        //membuat vertical box untuk menampung horizontal box dan tabel yang sebelumnya sudah dibuat
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, tableView);

        //menampilkan aplikasi
        Scene scene = new Scene(vBox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //fungsi untuk menampilkan pop up untuk update data
    private void showUpdatePopup(Mahasiswa mahasiswaToUpdate) {

        //bagian untuk update data
        Stage updateStage = new Stage();
        updateStage.setTitle("Perbarui Data");

        //membuat field/ruang untuk update data
        TextField updateNameField = new TextField(mahasiswaToUpdate.getNama());
        updateNameField.setPromptText("Nama Lengkap");

        TextField updateNimField = new TextField(mahasiswaToUpdate.getNim());
        updateNimField.setPromptText("NIM");

        TextField updateFacultyField = new TextField(mahasiswaToUpdate.getFakultas());
        updateFacultyField.setPromptText("Fakultas");

        TextField updateMajorField = new TextField(mahasiswaToUpdate.getJurusan());
        updateMajorField.setPromptText("Jurusan");

        TextField updateRoleField = new TextField(mahasiswaToUpdate.getPeran());
        updateRoleField.setPromptText("Peran");

        //membuat tombol konfirmasi untuk update data
        Button confirmUpdateButton = new Button("Konfirmasi");
        confirmUpdateButton.setOnAction(e -> {
            updateMahasiswa(mahasiswaToUpdate, updateNameField.getText(), updateNimField.getText(), updateFacultyField.getText(), updateMajorField.getText(), updateRoleField.getText());
            updateStage.close();
        });

        //box pengisian data
        VBox updateLayout = new VBox(10);
        updateLayout.setPadding(new Insets(20));
        updateLayout.getChildren().addAll(updateNameField, updateNimField, updateFacultyField, updateMajorField, updateRoleField, confirmUpdateButton);

        //pembuatan window pop up
        Scene updateScene = new Scene(updateLayout, 300, 250);
        updateStage.setScene(updateScene);
        updateStage.show();
    }

    //fungsi untuk update data mahasiswa
    private void updateMahasiswa(Mahasiswa mahasiswaToUpdate, String newName, String newNim, String newFaculty, String newMajor, String newRole) {
        if (!newName.isEmpty() && !newNim.isEmpty() && !newFaculty.isEmpty() && !newMajor.isEmpty() && !newRole.isEmpty()) {
            try {
                Integer.parseInt(newNim);

                // Perbarui data mahasiswa
                mahasiswaToUpdate.setNama(newName);
                mahasiswaToUpdate.setNim(newNim);
                mahasiswaToUpdate.setFakultas(newFaculty);
                mahasiswaToUpdate.setJurusan(newMajor);
                mahasiswaToUpdate.setPeran(newRole);

                // Perbarui tampilan tabel
                tableView.refresh();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error!", "NIM harus berupa angka!");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Input Error!", "Semua field harus diisi!");
        }
    }

    //fungsi untuk menampilkan pemberitahuan
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //fungsi untuk menyimpan data dalam bentuk txt
    private void saveData(List<Mahasiswa> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (Mahasiswa mahasiswa : data) {
            writer.write("Nama: " + mahasiswa.getNama() + ", NIM: " + mahasiswa.getNim() + ", Fakultas: " + mahasiswa.getFakultas() + ", Jurusan: " + mahasiswa.getJurusan() + ", Peran: " + mahasiswa.getPeran());
            writer.newLine();
        }
        writer.close();
    }

    //kelas untuk menampung data mahasiswa yang sudah dimasukkan
    public static class Mahasiswa {
        private String nama;
        private String nim;
        private String fakultas;
        private String jurusan;
        private String peran;

        public Mahasiswa(String nama, String nim, String fakultas, String jurusan, String peran) {
            this.nama = nama;
            this.nim = nim;
            this.fakultas = fakultas;
            this.jurusan = jurusan;
            this.peran = peran;
        }

        public String getNama() {
            return nama;
        }

        public String getNim() {
            return nim;
        }

        public String getFakultas() {
            return fakultas;
        }

        public String getJurusan() {
            return jurusan;
        }

        public String getPeran() {
            return peran;
        }

        public void setNama(String newName) {
            this.nama = newName;
        }

        public void setNim(String newNim) {
            this.nim = newNim;
        }

        public void setFakultas(String newFaculty) {
            this.fakultas = newFaculty;
        }

        public void setJurusan(String newMajor) {
            this.jurusan = newMajor;
        }

        public void setPeran(String newRole) {
            this.peran = newRole;
        }
    }
}