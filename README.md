# Responsi 1 Mobile (H1D023036) - OGC Nice

Aplikasi Android sederhana yang dibuat untuk memenuhi tugas Responsi 1 Praktikum Pemrograman Mobile. 
Aplikasi ini menampilkan informasi tentang klub sepakbola **OGC Nice**, yang datanya diambil secara *real-time* dari API `football-data.org`.

---

## Data Praktikan

| Keterangan | Isi |
| :--- | :--- |
| **Nama** | Defit Bagus Saputra |
| **NIM** | H1D023036 |
| **Shift Baru** | F |
| **Shift Asal** | C |

---

## Fitur Aplikasi

Aplikasi ini memiliki beberapa fitur yang sudah sesuai dengan ketentuan yang diperintahkan:

1.  **Halaman Utama:**
    * Menampilkan header (stadion), logo, dan nama klub OGC Nice.
    * Menampilkan deskripsi singkat klub.
    * Menyediakan 3 menu navigasi utama: "Club History", "Head Coach", dan "Team Squad".

2.  **Halaman Sejarah Klub (Club History):**
    * Halaman statis yang menampilkan sejarah lengkap OGC Nice di dalam `CardView` yang dapat di-*scroll*.

3.  **Halaman Pelatih (Head Coach):**
    * Menampilkan data pelatih (foto statis, nama, tanggal lahir, dan kebangsaan) yang **diambil dari API**.

4.  **Halaman Skuad Tim (Team Squad):**
    * Menampilkan seluruh daftar pemain dalam sebuah `RecyclerView`.
    * Setiap kartu pemain **diwarnai sesuai dengan kategori posisinya** (data dari API) seperti ketentuan:
        * **Kuning** untuk Goalkeeper.
        * **Biru** untuk Defender (termasuk "Defence", "Left-Back", "Centre-Back", "Right-Back").
        * **Hijau** untuk Midfielder (termasuk "Midfield", "Central Midfield", "Defensive Midfield").
        * **Merah** untuk Forward (termasuk "Offence", "Centre-Forward", "Left Winger", "Right Winger").

5.  **Fragment Detail Pemain:**
    * Ketika sebuah kartu pemain di-klik, sebuah `BottomSheetDialogFragment` akan muncul dari bawah.
    * *Fragment* ini menampilkan detail lengkap pemain (Nama, TTL, Kebangsaan, Posisi) yang **datanya berasal dari API**.
    * 
---

## Penjelasan Alur Data (API ke Layar)

Alur data pada aplikasi ini mengikuti arsitektur MVVM (Model-View-ViewModel) dan menggunakan `LiveData` serta `Coroutines` untuk menangani proses *asynchronous*.

### 1. Pemanggilan API (Main Activity)
Saat `MainActivity` dibuka, ia akan menginisialisasi `TeamViewModel`. `TeamViewModel` kemudian segera memanggil fungsi `fetchTeamData()` menggunakan `viewModelScope`. Ini adalah *Coroutine* yang memastikan pemanggilan API berjalan di *background thread* (tidak mengganggu UI).

### 2. Proses Jaringan (Retrofit)
`TeamViewModel` memanggil `TeamRepository` (sebagai *single source of truth*), yang kemudian memanggil `RetrofitInstance.api.getTeamDetail()`. Retrofit melakukan *request* `GET` ke *endpoint* `https://api.football-data.org/v4/teams/522` dengan menyertakan *Header* `X-Auth-Token` untuk otentikasi.

### 3. Parsing Data (Gson)
API `football-data.org` mengembalikan respons dalam format JSON. `GsonConverterFactory` yang terpasang di Retrofit secara otomatis mem-parsing (mengubah) data JSON tersebut menjadi *data class* Kotlin yang telah disiapkan: `TeamResponse`.

### 4. Penyajian Data (ViewModel & LiveData)
`TeamViewModel` menerima objek `TeamResponse` dari *repository*. ViewModel kemudian memperbarui nilai dari `_teamData`, yang merupakan `MutableLiveData`.

`MainActivity` "mengamati" (`observe`) `teamData`. Ketika data baru masuk, *observer* di `MainActivity` terpicu. `MainActivity` mengambil data (seperti `team.name` dan `team.crest`) dan menampilkannya di UI (memuat gambar logo menggunakan `Glide`).

### 5. Pengiriman Data ke Activity Lain (Intent)
Ketika pengguna menekan menu "Head Coach" atau "Team Squad", `MainActivity` mengambil data yang sudah tersimpan di `viewModel.teamData.value`.
* Objek `Coach` atau `List<SquadMember>` (yang sudah dibuat `Serializable`) dimasukkan ke dalam `Intent.putExtra()`.
* `CoachActivity` atau `SquadActivity` dimulai dengan membawa data tersebut.

### 6. Penyajian di Layar Skuad (RecyclerView)
`SquadActivity` menerima `List<SquadMember>` dari *intent*. Daftar ini kemudian diteruskan ke `SquadAdapter`.
Di dalam `SquadAdapter` (pada `onBindViewHolder`), setiap data `player` diperiksa `position`-nya (misal: "Left-Back", "Central Midfield", "Offence"). Sebuah `when` *statement* mengelompokkan 13+ posisi spesifik dari API menjadi 4 kategori (Defender, Midfielder, Forward, Goalkeeper) sesuai permintaan soal. Warna kartu (`CardView`) diatur berdasarkan kategori tersebut.

### 7. Penyajian di Layar Detail (Fragment)
Saat sebuah kartu pemain di-klik, `SquadAdapter` mengambil data `player` yang diklik beserta `colorId` kartu tersebut.
Data ini dikirim ke `PlayerDetailFragment` melalui *Bundle* (`newInstance`). `PlayerDetailFragment` (sebuah `BottomSheetDialogFragment`) ditampilkan, membaca data dari *Bundle*-nya, dan menampilkan detail pemain serta *border* berwarna di bagian atas.

---

## Video Demo Aplikasi

Berikut adalah video demo yang menampilkan fungsionalitas aplikasi dan ikon aplikasi (sesuai ketentuan):

![Demo Aplikasi](demo/demo_app.gif)
