import java.util.Scanner ;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class AljabarLanjar {
	int i,j,imax,jmax,n;
	double inter;
	boolean banyaksolusi;
	double[][] data,data2;
	class titik {
			double x,y;
		}
	titik[] tabtitik;
	String namafile;

		
	AljabarLanjar() {
		imax = 0;
		jmax = 0;
		data = new double[20][20];
		data2 = new double[20][20];
		tabtitik = new titik[100];
		for (i=1;i<=99;i++) {
		tabtitik[i] = new titik();
		}
	}
	
	void tulismatriks() {
		for(i=1;i<=imax;i++) {
			for(j=1;j<=jmax;j++) {
				System.out.print(String.format("%6.2f",data[i][j])+" ");
			}
			System.out.println("");
		}	
	}
	
	void keyboard_spl() {
		Scanner S;
		S = new Scanner (System.in);
		System.out.println("----------------------------");
		System.out.println("Masukkan Matriks Augmented :");
		System.out.println("----------------------------");
		System.out.print("Jumlah Kolom : ");
		jmax = S.nextInt();
		System.out.print("Jumlah Baris : ");
		imax = S.nextInt();
		System.out.println("Masukkan Matriks : ");
		for (i=1;i<=imax;i++) {
			for (j=1;j<=jmax;j++) {
				data[i][j] = S.nextDouble();
			}
		}
	
	}
	
	void OBE_geser(int a,int b) {
		double temp;
		for (j=1;j<=jmax;j++) {
			temp = data[a][j];
			data[a][j] = data[b][j];
			data[b][j] = temp;
		}
	}
	
	void OBE_bagikons(int baris,double k) {
		for(j=1;j<=jmax;j++) {
			data[baris][j] = data[baris][j] / k;
		}
	}
		
	void OBE_perslain(int baristarget, int barislain, double k) {
		for(j=1;j<=jmax;j++){
			data[baristarget][j] = data[baristarget][j] + (k * data[barislain][j]);
		}
	}
		
	boolean IsKolomZero(int kolom, int bawal , int bakhir) {
		boolean nolsemua = true;
		for (i=bawal;i<=bakhir;i++) {
			if (data[i][kolom] != 0) {
				nolsemua = false;
			}
		}
		return nolsemua;
	}
	
	boolean IsBarisZero(int baris) {
		boolean nolsemua = true;
		for(i=1;i<=jmax;i++){
			if ( data[baris][i] != 0) {
				nolsemua = false;
			}
		}
		return nolsemua;
	}
	
	void pemorosan() {

		
		System.out.println("----------------------------------------------------------------");
			System.out.println("Pemrosesan Matriks");
			System.out.println("----------------------------------------------------------------");

		int poros,kolom,last_kolom,last_baris,n,nproses;
		n = 1;
		kolom = 1;
		nproses = imax;
		last_kolom = jmax;
		last_baris = imax;
		while (n <= nproses) {
			do {
				if (IsKolomZero(kolom,n,imax) == true) 
					kolom++;
			}
			while ( IsKolomZero(kolom,n,imax) == true);
			
			if (IsBarisZero(n) == true) {
				nproses--;
			}
			
			poros=n;
			for (i=n+1;i<=imax;i++) {
				if ( Math.abs(data[i][kolom]) > Math.abs(data[poros][kolom]) ) {
					poros = i;
				}
			}
			
			if (n != poros) {
			OBE_geser(n,poros);
			
			System.out.println("OBE : Geser "+n+" <-> "+poros);
			System.out.println("");
			tulismatriks();
			System.out.println("");
			}
			
		
			System.out.println("OBE : Baris ke-"+n+" dibagi "+data[n][kolom]);
			System.out.println("");
			if (data[n][kolom] != 0) {
				OBE_bagikons(n,data[n][kolom]);
			}
			
			tulismatriks();
			System.out.println("");
			
			last_kolom = kolom;
			last_baris = n;
			
			if (n != imax) {
			for(i=n+1;i<=imax;i++) {
				OBE_perslain(i,n,(-1)*data[i][kolom]);
			}
		
			System.out.println("OBE : Jadikan nol..");
			System.out.println("");
			tulismatriks();
			System.out.println("");
			}
			
			
			kolom++;
			n++;
			nproses = Rank();
			
		}
		
		
	
		while (last_baris != 1) {
		
			do {
				if (data[last_baris][last_kolom] == 0) {
					last_kolom--;
				}
			}
			while ((data[last_baris][last_kolom] == 0) );
			
			System.out.println("");
			System.out.println("OBE : Backward phase, baris ke-"+last_baris);
			System.out.println("");
			for (i=last_baris-1;i>=1;i--) {
				OBE_perslain(i,last_baris, (-1) * data[i][last_kolom]);
			}
			tulismatriks();
			
			
			last_baris--;
			last_kolom--;
			if (last_baris == 1) {
				break;
			}
			if( (data[last_baris][last_kolom] != 1) ) {
				do {
					last_kolom--;
				}
				while (data[last_baris][last_kolom] != 1);
			}
		}
		
		if ((IsBarisZero(imax) == true) || ((jmax-1) > imax) ) {
			banyaksolusi = true;
		}
		else {
			banyaksolusi = false;
		}
		
			System.out.println("----------------------------------------------------------------");
			System.out.println("Proses Selesai...");
			System.out.println("----------------------------------------------------------------");
			System.out.println("");
	
	}
	
	
	boolean IsTanpaSolusi() {
		boolean temp;
		temp = false;
		int x = imax;
		if (IsBarisZero(x) == true) {
			do {
				x--;
			}
			while (IsBarisZero(x) == true);
		}
		if ((data[x][jmax] == 1) && (data[x][jmax-1] == 0) ) {
			return true;
		}
		else {
			return false;
		}
		
	
	}
	

	
	
	
	int Rank() {
		int temp = 0;
		int x;
		for(x=1;x<=imax;x++) {
			if (IsBarisBukanZero(x) == true) {
				temp++;
			}
		}
		return temp;
	
	
	
	}
	
	boolean IsBanyakSolusi() {
		int temp = 0;
		int x;
		for(x=1;x<=imax;x++) {
			if (IsBarisBukanZero(x) == true) {
				temp++;
			}
		}
		if ((jmax-1) > temp ) {
			return true;
		}
		else {
			return false;
		}
	
	}
	
	boolean IsBarisBukanZero(int baris) {
		boolean temp;
		temp = false;
		for (i=1;i<=jmax;i++) {
			if (data[baris][i] != 0) {
				temp = true;
			}
		}
		return temp;
	
	}
	
	
	
	
	
	void kesimpulan() {
		int nvar;
		char[] param;
		char[] mapping;
		param = new char[9];
		mapping = new char[100];
		String[] kefile;
		kefile = new String[100];
		int nline;
		boolean ftoken;
		nline=0;
		
		
		if ( IsTanpaSolusi() == true) {
				System.out.println("Tidak ada solusi");
				nline++;
				kefile[nline]="Tidak ada solusi";
			}
		
		
		else if ( IsBanyakSolusi() == true) {
			
			System.out.println("Banyak solusi");
			nline++;
			kefile[nline]="Banyak solusi";
			param[1]='s';
			param[2]='t';
			param[3]='u';
			param[4]='v';
			param[5]='w';
			param[6]='x';
			param[7]='y';
			param[8]='z';
			for (j=1; j<=(jmax-1) ;j++) {
				mapping[j]='0';
			}
			i=1;
			j=1;
			nvar=1;
			
			while ( (j < jmax) && (i<=imax) ) {
				if (data[i][j] == 1) {
					System.out.print("x"+j+" = ");
					kefile[nline]="x"+j+" = ";
					j++;
					ftoken=true;
					if (data[i][jmax] != 0) {
						System.out.print(data[i][jmax]);
						kefile[nline]=kefile[nline]+data[i][jmax];
						ftoken=false;
					}
					
					if (j != jmax) {
						
						do {
							if (data[i][j] <= 0.01) {
								j++;
							}
							else {
								if (i==1) {
									mapping[j]=param[nvar];
									nvar++;
								}
								if (ftoken == false) {
									System.out.print(" + ");
									System.out.print("("+ (-1)*data[i][j] + mapping[j]+")");
									kefile[nline]=kefile[nline]+" + ";
									kefile[nline]=kefile[nline]+"("+ (-1)*data[i][j] + mapping[j]+")";
									
								}
								else {
									
									System.out.print("("+ (-1)*data[i][j] + mapping[j] + ")");
									kefile[nline]=kefile[nline]+"("+ (-1)*data[i][j] + mapping[j]+")";
									ftoken = false;
								}
								j++;
							}
						} while ( j != jmax);
						System.out.println("");
						nline++;
						j=1;
						i++;
					}
					}
					else {
					j++;
					if (j >= jmax) {
						j = 1;
						i++;
					}
				}
				}
				
			System.out.println("");
				for (j=1;j<=(jmax-1);j++) {
					if (mapping[j] != '0') {
						System.out.println("x"+j+" = "+mapping[j]);
						nline++;
						kefile[nline]="x"+j+" = "+mapping[j];
					}
				}
			}
				
			else {

				for(i=1;i<=imax;i++) {
					System.out.println("x"+i+" = "+data[i][jmax]);
					nline++;
					kefile[nline]="x"+i+" = "+data[i][jmax];
				}	
			
			}
			
			try {
			PrintStream out = new PrintStream(new FileOutputStream("keluaran_"+namafile));
			for (i=1;i<=nline;i++) {
			out.println(kefile[i]);
			}
			out.close();
		} 
		catch (FileNotFoundException e) {
		  e.printStackTrace();
		}
		}
	
	void interpolasi() {
		
		int j,k,derajat;
		Scanner S;
		S = new Scanner (System.in);
		
		double temp,hasil;
		System.out.print("Masukkan derajat interpolasi (2-"+imax+") : ");
		derajat = S.nextInt();
		while ((derajat < 2) || (derajat >imax) ) {
			System.out.println("Masukkan salah,ulangi ");
			System.out.print("Masukkan derajat interpolasi (2-"+imax+") : ");
			derajat = S.nextInt();
		}
	
		n = derajat;
		
		
		System.out.print("Titik interpolasi : ");
		inter = S.nextDouble();
		
	
		imax = n;
		jmax = n+1;
		for (i=1;i<=imax;i++) {
			for (j=1;j<=jmax;j++) {
				if (j==1) {
					data[i][j] = 1;
				}
				else if (j == jmax){
					data[i][j] = tabtitik[i].y;
				}
				else {
					temp=tabtitik[i].x;
					for (k=1;k<=(j-2);k++) {
						temp=temp*tabtitik[i].x;
					}
					data[i][j] = temp;
				}
			}
		}
		pemorosan();
		System.out.println("Polinom interpolasi");
		System.out.print("P(x)	= ");
		System.out.print("("+String.format("%.4f",data[1][jmax])+")");
		
		for(i=2;i<=imax;i++) {
			System.out.print(" + ");
			System.out.print("("+String.format("%.4f",data[i][jmax])+"x^"+(i-1)+")");
			
		}
		System.out.println("");
		
		hasil = data[1][jmax];
		for (j=2;j<=n;j++){
			temp = inter;
			for (i=2;i<=(j-1);i++) {
				temp = temp*inter;
			}
			hasil = hasil + (temp* data[j][jmax]);
		}
		System.out.print("");
		System.out.println("P("+inter+")	= "+hasil);
		
		 try {
			PrintStream out = new PrintStream(new FileOutputStream("keluaran_"+namafile));
			out.println("P("+inter+") = "+hasil);
			out.close();
		} 
		catch (FileNotFoundException e) {
		  e.printStackTrace();
		}
	
		
		
	}
	
	
	void key_interpolasi() {
		Scanner S;
		S = new Scanner (System.in);
		
		System.out.print("Jumlah titik : ");
		n = S.nextInt();
		
		System.out.println("Masukkan titik dengan format x (spasi) y");
		for (i=1;i<=n;i++) {
			tabtitik[i].x = S.nextDouble();
			tabtitik[i].y = S.nextDouble();
		}
		System.out.println("Titik interpolasi");
		inter = S.nextDouble();
		
	
	}
	
	
	void file_spl() {
        Scanner S1 ;
        String filename ;
        S1 = new Scanner (System.in);
        filename = new String();
        //filename = "src/test.txt";
        System.out.print("Masukkan nama file	: ");
        filename = S1.nextLine();
		namafile = filename;
        try {
			System.out.println("");
			System.out.println("----------------------------------------------------------------");
            System.out.println("File "+filename+" ditemukan");
			System.out.println("----------------------------------------------------------------");
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 0;
            int col = 0;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                col = vals.length; 
                row++;
            }
            imax = row ;
            jmax = col ;
            data = new double[row+1][col+1];
            
			
        }
        catch (IOException e) {System.out.println("File tidak ada");}
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 1;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                for (int i = 1; i <= jmax; i++) {
                    data[row][i] = Double.parseDouble(vals[i-1]);
                }
                row++;
            }
        } catch (IOException e) {} 
		tulismatriks();
		System.out.println("");
    }
	
		void file_interpolasi() {
        Scanner S1 ;
        String filename ;
        S1 = new Scanner (System.in);
        filename = new String();
        //filename = "src/test.txt";
        System.out.println("Masukkan nama file	: ");
        filename = S1.nextLine();
		namafile = filename;
        try {
            System.out.println("Berhasil membuka file "+filename);
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 0;
            int col = 0;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                col = vals.length; 
                row++;
            }
            imax = row ;
            jmax = col ;
            data2 = new double[row+1][col+1];
            
        }
        catch (IOException e) {System.out.println("File tidak ada");}
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            BufferedReader buffer = new BufferedReader(new FileReader(filename));
            String line;
            int row = 1;
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");
                for (int i = 1; i <= jmax; i++) {
                    data2[row][i] = Double.parseDouble(vals[i-1]);
                }
                row++;
            }
        } catch (IOException e) {} 
		
		for(i=1;i<=imax;i++){
				tabtitik[i].x = data2[i][1];
				tabtitik[i].y = data2[i][2];
				System.out.println(i+" : <"+tabtitik[i].x+" , "+tabtitik[i].y+">");
		}
		
			
			n = imax;
    }
    
	
	
	
	public static void main(String[] args) {
		AljabarLanjar M;
		M = new AljabarLanjar();
		Scanner S;
		S = new Scanner(System.in);
		boolean valid = true;
		boolean stop = false;
		int choice;
		do {
		System.out.println("----------------------------------------------------------------");
		System.out.println("Aplikasi Aljabar Lanjar");
		System.out.println("----------------------------------------------------------------");
		System.out.println(" 1 : Penyelesaian SPL (input keyboard)");
		System.out.println(" 2 : Penyelesaian SPL (file) ");
		System.out.println(" 3 : Interpolasi (input keyboard) ");
		System.out.println(" 4 : Interpolasi (file) ");
		System.out.println("");
		do {
		System.out.print("Masukkan pilihan	: ");
		choice = S.nextInt();
		if ( (choice > 4) || (choice <= 0) ) {
			System.out.println("Masukan salah , ulangi...");
			valid = false;
			}
		else {
			valid = true;
		}
		}
		
		while (valid == false);
		
		switch (choice) {
			case 1 : 
				M.keyboard_spl();
				M.pemorosan();
				M.tulismatriks();
				M.kesimpulan();
				System.out.println("Output program tersimpan pada file keluaran.txt...");
				break;
			case 2 :
				M.file_spl();
				M.pemorosan();
				M.kesimpulan();
				System.out.println("Output program tersimpan pada file keluaran.txt...");
				break;
			case 3 :
				M.key_interpolasi();
				M.interpolasi();
				System.out.println("Output program tersimpan pada file keluaran.txt...");
				break;
			case 4 :
				M.file_interpolasi();
				M.interpolasi();
				System.out.println("Output program tersimpan pada file keluaran.txt...");
				break;
				

		}
		System.out.println("");
		System.out.println("Masukkan 0 untuk keluar dari program");
		System.out.println("Masukkan 1 untuk melanjutkan program");
		choice = S.nextInt();
		if (choice == 0) {
			stop=true;
		}
		}
		while (stop == false);
		
		
		
	}
}