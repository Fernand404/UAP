# APLIKASI REGISTER UKM
Aplikasi ini bertujuan untuk memudahkan input data mahasiswa yang akan bergabung ke ukm

# Tata Cara Penggunaan :
1. input data yang akan ditambahkan lalu klik tambah
2. jika ingin update data maka klik data yang ada di dalam tabel kemudian akan ada pop up untuk mengupdate data, isi datanya lalu klik selesai
3. jika ingin menghapus data maka klik data yang ada dalam tabel lalu klik hapus
4. jika data sudah benar semua, dapat di print kedalam file.txt dengan tombol simpan

------------------------------------------------------------------------------------------

1. Tujuan Program
- Membuat register UKM
- penambahan data mahasiswa yang ingin mendaftar ukm

2. Antarmuka Program
- terdapat tabel-tabel yang berisi data mahasiswa
- tombol tambah untuk menambah data
- tombol hapus untuk menghapus data
- tombol update untuk mengupdate data
- tombol simpan untuk menyimpan data kedalam file.txt

3. Fungsionalitas
- menginput data mahasiswa yang kemudian akan masuk kedalam tabel
- update data yang sudah ada jika data itu salah
- hapus data jika mahasiswa tersebut tidak jadi masuk ukm

4. Data pengguna
- data pengguna disimpan dalam tabel
- data tersebut dapat dihapus atau di update

5. Validasi
- kolom harus dipastikan tidak kosong

6. penanganan kesalahan
- kotak input data tidak boleh kosong, dan nim harus berbentuk angka

------------------------------------------------------------------------------------------

## Informal Problem
Parameter : Nama(String), NIM(int), fakultas(String), jurusan(String), alamat(String)

Returns : Data masuk kedalam tabel sesuai gengan data yang diinput

## Formal Program Spesification
1. fungsi tambah

{fieldsAreNotEmpty() ∧ isNumeric(nimField.getText)} addButton.setOnAction {mahasiswaIsAddedToTableView()}
2. fungsi update

{mahasiswaIsSelected()} updateButton.setOnAction {selectedMahasiswaIsUpdated()}
3. fungsi hapus

{mahasiswaIsSelected()} deleteButton.setOnAction {selectedMahasiswaIsDeleted()}
4. fungsi simpan

{data ≠ null ∧ data.containsOnlyValidMahasiswa() ∧ FileIsWritable("output.txt")} saveData(data) {dataIsWrittenToFile("output.txt")}
