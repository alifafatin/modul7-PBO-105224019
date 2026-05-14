class MetodePembayaran{
    void bayar(double nominal){
        System.out.println("Memproses pembayaran umum sebesar: Rp " + nominal + "...");
    }
}

class EWallet extends MetodePembayaran{
    @Override
    void bayar(double nominal){
        System.out.println("Memotong saldo E-Wallet sebesar: Rp " + nominal + "...");
    }

    void bayar(double nominal, String nomorHp){
        System.out.println("Memotong saldo E-Wallet sebesar: Rp " + nominal + " dari nomor HP: " + nomorHp + "...");
    }
}

class KartuKredit extends MetodePembayaran{
    @Override
    void bayar(double nominal){
        System.out.println("Mencetak tagihan kartu kredit sebesar: Rp " + nominal + "...");
    }

    void verifikasiPIN(){
        System.out.println("Memverifikasi PIN kartu kredit... BERHASIL!");
    }
}
public class Main {
    public static void main(String[] args){
        MetodePembayaran[]  pembayaran = new MetodePembayaran[2];
        pembayaran[0] = new EWallet();
        pembayaran[1] = new KartuKredit();

        for (MetodePembayaran metode : pembayaran){
            metode.bayar(100000);

            if (metode instanceof EWallet){
                EWallet ewalet = (EWallet) metode;
                ewalet.bayar(50000, "081234567890");
            }

            if (metode instanceof KartuKredit){
                KartuKredit kartukredit = (KartuKredit) metode;
                kartukredit.verifikasiPIN();
            }
        }
    }
}
