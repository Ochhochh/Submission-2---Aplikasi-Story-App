# Submission-2---Aplikasi-Story-App
Submission Pengembang Aplikasi Android by Dicoding Academy

Fitur yang harus ada pada aplikasi.

1. Mempertahankan Fitur dari Submission Sebelumnya
   Syarat yang harus dipenuhi sebagai berikut:
   - Pastikan berbagai fitur yang ada dalam submission sebelumnya berjalan dengan baik.

2. Menampilkan Maps
   Syarat yang harus dipenuhi sebagai berikut:
   - Menampilkan satu halaman baru berisi peta yang menampilkan semua cerita yang memiliki lokasi dengan benar. Bisa berupa marker maupun icon berupa gambar. Data story yang memiliki lokasi latitude dan longitude dapat diambil melalui parameter location seperti berikut
     - https://story-api.dicoding.dev/v1/stories?location=1.

3. Paging List
   Syarat yang harus dipenuhi sebagai berikut:
   - Menampilkan list story dengan menggunakan Paging 3 dengan benar.

4. Membuat Testing
   Syarat yang harus dipenuhi sebagai berikut:
   - Menerapkan unit test pada fungsi di dalam ViewModel yang mengambil list data Paging.
   - Berikut skenario yang harus Anda buat.
     - Ketika berhasil memuat data cerita.
       - Memastikan data tidak null.
       - Memastikan jumlah data sesuai dengan yang diharapkan.
       - Memastikan data pertama yang dikembalikan sesuai.
     - Ketika tidak ada data cerita.
       - Memastikan jumlah data yang dikembalikan nol.
