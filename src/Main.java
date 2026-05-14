import java.util.ArrayList;//untuk array list, jika tidak menggunakan ini maka nantinya akan eror
//class induk menjadi blueprint pada semua layanan ekspedisi
//1. membuat superclass layanan ekspedisi
class LayananEkspedisi{
    //membuat atribut dengan menggunakan modifier protected agar bisa diakses subclass
    protected  String nomorResi;
    protected double beratAktualKg;
    protected double panjang;
    protected double lebar;
    protected double tinggi;

    //membuat konstruktor untuk memanggil atribut yang sudha dibuat, dipanggi ketika objek dibuat untuk mengisi atribut
    public LayananEkspedisi(String nomorResi, double beratAktualKg, double panjang, double lebar, double tinggi){
        this.nomorResi = nomorResi;
        this.beratAktualKg = beratAktualKg;
        this.panjang = panjang;
        this.lebar = lebar;
        this.tinggi = tinggi;
    }

    //membuat metode double yang mengembalikan nilai terteinggi antara berat altual dan berat volumetrik dengan rumus
    //(Rumus volumetrik = (P x L x T) / 6000).

    public double hitungBeratEfektif(){
        double beratVolumetrik = (panjang * lebar * tinggi) / 6000;
        if (beratAktualKg > beratVolumetrik){
            return beratAktualKg; //jika berat aktual lebih besar maka yang dikembalikan adalah nilai berat aktual
        } else {
            return beratVolumetrik; //jika ternyata berat aktual lebih kecil maka yang dikembalikan adalah nilai berat volumetrik
        }
    }

    //membuat metode void cetakResi() yang fungsinya untuk mencetak nomor resi dan berat efektiif yang digunakan  untuk pengiriman nantinya
    void cetakResi(){
        System.out.println("Nomor Resi: " + nomorResi);
        System.out.println("Berat Efektif: " + hitungBeratEfektif() + " kg");
    }

    //metode hitungOngkir() digunakan untuk mengembalikan nilai 0.0 sebagai nilai default
    //karena setiap subclass mempunyai angka yang berbeda
    //nantinya akan di override oleh masing-masing subclass untuk menghitung ongkir sesuai dengan tarif yang berlaku

    public double hitungOngkir(){
        return 0.0; //nilai defaultnya 
    }
}

//2. membuat subclass untuk layanan reguler yang mewarisi kelas LayananEkspedisi
class LayananReguler extends LayananEkspedisi{
    //konstruktor langsung karena tidak ada atribut tambahan yang diminta
    public LayananReguler(String nomorResi, double beratAktualKg, double panjang, double lebar, double tinggi) {
        super(nomorResi, beratAktualKg, panjang, lebar, tinggi);
    }
   @Override //override berfungsi untuk menggantikan metode hitungOngkir() yang ada di kelas induk dengan implementasi yang sesuai dengan tarif layanan reguler
   //tarif dasarnya adalah 15.000/kg, dihitung dari berat efektif nya 
   public double hitungOngkir(){
    return hitungBeratEfektif() * 15000;
   }

   //overloading
   //membuat metode double hitungOngkir dengan aturan 
   //mengambil tarif dasar, jika member maka akan diberikan diskon 10% dari tarif dasar, setelah dipotong diskon maka akan ditambahkan surcharge jarak jayh sebesar Rp500 per jarakKm

   public double hitungOngkir(boolean isMember, int jaraKm){
    double tarifDasar = hitungOngkir(); //mengambil tarif dasar dari metode hitungOngkir() yang sudah di override sebelumnya
    if (isMember){
        tarifDasar = tarifDasar - (tarifDasar * 0.10); //jika member maka akan diberikan diskon 10% dari tarif dasar
    }

    tarifDasar = tarifDasar + (jaraKm * 500); //setelah dipotong diskon maka akan ditambahkan surcharge jarak jauh sebesar Rp500 per jarakKm
    return tarifDasar; //mengembalikan nilai tarif dasar yang sudah dihitung
   }
}

//3. membuat subclass untuk layanan ekspress yang akan mewarisi layananEKspedisi
class LayananEkspress extends LayananEkspedisi{
    //konstruktor langsung karena tidak ada atribut tambahan yang diminta
    public LayananEkspress(String nomorResi, double beratAktualKg, double panjang, double lebar, double tinggi) {
        super(nomorResi, beratAktualKg, panjang, lebar, tinggi);
    }

    @Override //override hitungOngkir(), agar sesuai dengan tarif layanan, tarif dasar 30.000 per kg akan dihitung dari berat efektif
    public double hitungOngkir(){
        return hitungBeratEfektif() * 30000;
    }

    //metode spesifik hanya ada di layanan ekspress, isinya untuk klaim asuransi. jika nilai barang > 1jt maka akan keluaer pesan Klaim Asuransi VIP Rp[nilai] untuk resi [nomorResi] sedang diproses
    //prioritas.". Jika tidak, cetak: "Klaim Asuransi Standar diproses dalam 7 hari kerja."

    public void klaimAsuransi(double nilaiBarang){
        if(nilaiBarang > 1000000){
            System.out.println("Klaim Asuransi VIP Rp " + nilaiBarang + "untuk resi " + nomorResi + " sedang diproses. Prioritas!!");
        } else {
            System.out.println("Klaim Asuransi Standar diproses dalam 7 hari kerja.");
        }
    }
}

//4. membuat subclass layanan internasional 
//yang mewarisi layanan ekspedisi, terdapat atribut tambahan yaitu negaraTujuan dan nilaibarangUSD karena ini merupakan transaksi internasional

class LayananInternasional extends LayananEkspedisi{
    protected String negaraTujuan;
    protected double nilaiBarangUSD;

    //konstruktor untuk memanggil semua atribut yang sudah dibuat, termasuk atribut yang diwarisi dari kelas induk dan atribut tambahan yang ada di kelas ini
    public LayananInternasional(String nomorResi, double beratAktualKg, double panjang, double lebar, double tinggi, String negaraTujuan, double nilaiBarangUSD) {
        super(nomorResi, beratAktualKg, panjang, lebar, tinggi);
        this.negaraTujuan = negaraTujuan;
        this.nilaiBarangUSD = nilaiBarangUSD;
    }

    @Override //override hitungOngkir() dengan aturan yang sudah ditetapkan yaitu 
    // tarif ongkir dasar adalah 200000 per kg (dari berat efeiktid), pajak bea cukai itu
    //jika nilaiBarangUSD lebih dari 50 USD, maka dikenakan pajak
    //sebesar 20% dari nilai ongkir dasar tersebut 
    //nilai yang dikembalikan adalah total dari ongkir ditambah pajak jiaka ada
    
    public double hitungOngkir(){
        //menghitung ongkir dasar terlebih dahulu
        double ongkirDasar = hitungBeratEfektif() * 200000;
        double pajak = 0.0; //nilai pajak defaultnya 0.0

        //cek apakaha terkena beacukai
        if(nilaiBarangUSD > 50){
            pajak = ongkirDasar *0.20;
        }

        //kembalikan total ongkir yang sudah ditambah dengan pajak jika ada
        return ongkirDasar + pajak;
    }

    //metode spesifik hanya ada didalam layanan internasional
    //untuk mencetak pesan atau mainifest pengiriman internasional
    //Buat void cetakManifest(). Cetak: "Manifest Internasional ke
    //[negaraTujuan] - Deklarasi Nilai: $[nilaiBarangUSD]"

    public void cetakManifest(){
        System.out.println("Manifest Internasional ke " + negaraTujuan + " - Deklarasi Nilai: $" + nilaiBarangUSD);
    }
}

//5. membuat kelas utama atau main dengan menggunakan array list :)
public class Main{
    public static void main(String[] args){
        //membuat arraylist <layananEkspedisi> (upcasting)
        ArrayList<LayananEkspedisi> daftarPengiriman = new ArrayList<>();

        //membuat daftar atau data ke daftarpengiriman 
        //laynanan reguler (resi REG-11. berat aktual 2kg, panjang 50cm, lebar 50cm, tinggi 50cm)
        daftarPengiriman.add(new LayananReguler("REG-11", 2, 50, 50, 50));

        //layanan ekspress (Resi EXP-22, berat aktual 5kg, panjang 10cm, lebar 10cm, tinggi 10cm)
        daftarPengiriman.add(new LayananEkspress("EXP-22", 5, 10, 10, 10));

        //layanan internasional (Resi INT-33, berat aktual 3kg, panjang 20cm, lebar 20cm, tinggi 20cm, negara tujuan "Korea", nilai barang 100 USD)
        daftarPengiriman.add(new LayananInternasional("INT-33", 3, 20, 20, 20, "Korea", 100));

        //buat var totalPendapatanPerusahaan = 0.0
        //menggunakan perulangan untuk melusuri isi daftarpengirim
        double totalPendapatanPerusahaan = 0.0;

        //menulusuri layanan satu per satu, anggaplah layanan adalah variabel sementara yang bertipe LayananEKspedisi
        //nantinya setiap  layanan akan berisi objek dari daftarPengiriman yang sedang dilusuri, karena tipe data layanan adalah LayananEkspedisi maka bisa menampung objek dari semua subclassnya (reguler, ekspress, internasional)

        for(LayananEkspedisi layanan : daftarPengiriman){ //perulangan akan dilakukan sebanyak jumlah elemen pada daftarpengiriman
            System.out.println("====================================");
            //cetak resi untuk setiap laynanan yang sedang dilusuri
            layanan.cetakResi();

            //panggil hitungOngkir() untuk setiap laynaan 
            double ongkir = layanan.hitungOngkir();
            totalPendapatanPerusahaan += ongkir; //tambahkan ongkir ke total pendapatan perusahaan
            System.out.println("Ongkir Dasarnya: Rp " + ongkir);

            //ngecek apakah objek merupakan layanan reguler 
            //jika iya maka downcast agar bisa akses hitungOngkir(boolean, int)
            if(layanan instanceof LayananReguler){
                LayananReguler reguler = (LayananReguler) layanan; //downcast layanan jadi layanan reguler
                //Jika Reguler: panggil hitungOngkir(true, 25) dan tampilkan harganya.  
                double ongkirMember = reguler.hitungOngkir(true, 25);
                System.out.println("Ongkir untuk Member dengan Jarak 25km: Rp " + ongkirMember);
            }

            //Jika Express: panggil klaimAsuransi(1500000).
            if(layanan instanceof LayananEkspress){
                LayananEkspress ekspress = (LayananEkspress) layanan; //downcast layanan jadi layanan ekspress
                ekspress.klaimAsuransi(1500000);
            }

            //Jika Internasional: panggil cetakManifest().
            if(layanan instanceof LayananInternasional){
                LayananInternasional internasional = (LayananInternasional) layanan; //downcast layanan jadi layanan internasional
                internasional.cetakManifest(); //metode spesifik cetak manifest()
            }
        }
        //Di akhir program, cetak total pendapatan keseluruhan perusahaan dari ongkir dasar
        //(sebelum promo member/jarak).
        System.out.println("====================================");
        System.out.println("Total Pendapatan Perusahaan: Rp " + totalPendapatanPerusahaan);
    }
}