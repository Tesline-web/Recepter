package models;

public class Client {

    private int id;
    private String brand;
    private String mail;
    private String siret;
    private String adress;
        public Client(int id, String brand, String mail, String siret, String adress){
            this.brand = brand;
            this.mail = mail;
            this.siret = siret;
            this.adress = adress;
            this.id = id;
        }
        @Override
        public String toString(){
            return this.id + "," + this.brand;
        }

        public int getId(){
            return this.id;
        }
}
