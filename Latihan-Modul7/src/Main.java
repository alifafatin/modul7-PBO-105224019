class PerangkatPintar{
// soal no 1
    void aktifkan(){
        System.out.println("Perangkat Pintar diaktifkan");
    }
}

class LampuPintar extends PerangkatPintar{
// soal no 2
    @Override
    void aktifkan(){
        System.out.println("Lampu menyala dengan tingkat kecerahan standar");
    }


    void aturKecerahan (int level){
        System.out.println("Kecerahan lampu diatur ke level " + level + "%. ");
    }

    void aturKecerahan (int level, String warna){
        System.out.println("Kecerahan lampu diatur ke level " + level + "% dengan warna cahaya" + warna);
    }
}

class AcPintar extends PerangkatPintar{
    void aktifkan(){
        System.out.println("AC menyala dan mulai mendinginkan ruangan");
    }

    void aturSuhu (int suhu){
        System.out.println("Suhu ruangan diatur ke " + suhu + " derajat.");
    }
}

public class Main{
    public static void main(String[] args){
    //No 3
    PerangkatPintar[] koleksi = new PerangkatPintar[2];

    koleksi[0] = new LampuPintar();
    koleksi[1] = new AcPintar();

        for (PerangkatPintar perangkat : koleksi){
            perangkat.aktifkan();
            //Pertanyaan: Analisis: Mengapa pesan yang tercetak bisa berbeda (sesuai perangkat aslinya) padahal referensi variabel yang memanggilnya sama-sama bertipe PerangkatPintar? Jelaskan
            //Jawaban: karena pesan yang tercetak berbeda dengan perangkat aslinya karena konsep polimorfisme dalam kasus ini, aktifkan() merupakan methode yang di override oleh lampu pintar dan ac pintar, 

            //No 4
            if(perangkat instanceof LampuPintar){
                LampuPintar lampu = (LampuPintar) perangkat;
                lampu.aturKecerahan(75);
                lampu.aturKecerahan(50, "kuning");
            } if (perangkat instanceof AcPintar){
                AcPintar ac = (AcPintar) perangkat;
                ac.aturSuhu(24);
            }
        }

        //no 5
        //sebelumnya terjadi eror karena alat1 merupakan perangkat pintar bukan lampu pintar compiler hanya melihat aturKecerahan()
         PerangkatPintar alat1 = new LampuPintar();
        if (alat1 instanceof LampuPintar) {
            LampuPintar lampu = (LampuPintar) alat1; 
            lampu.aturKecerahan(75, "Putih");
        }
    }   
}
