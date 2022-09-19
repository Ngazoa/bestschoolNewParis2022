/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import java.io.Serializable;

/**
 *
 * @author Michelle Wafo
 */
public class model_notes_eleves implements Serializable{
      private String nom,date,lnaissance,sexe,classe;
      private String total,moyenne,rang;
      private String 
 note1,note2,note3,note4,note5,note6,note7,note8,note9,note10,note11,note12,note13,note14,note15,note16,note17,note18,note19,note20,note21,note22;

//  
//        this.nom = name;
//        this.date= date;
//        this.lnaissance = lnaissance;
//        this.sexe= sexe;
//        this.classe=classe;
//        this.note1=note1;
//        this.note2=note2;
//        this.note3=note3;
//        this.note4=note4;
//        this.note5=note5;
//        this.note6=note6;
//        this.note7=note7;
//        this.note8=note8;
//        this.note9=note9;
//        this.note10=note10;
//        this.note11=note11;
//        this.note12=note12;
//        this.note13=note13;
//        this.note14=note14;
//        this.note15=note15;
//        this.note16=note16;
//        this.note17=note17;
//        this.note18=note18;
//        this.note19=note19;
//        this.note20=note20;
//        this.note21=note21;
//        note22=note22;

      public void sexe(String s) {
        this.sexe= s;
    }
    
     public String sexe() {
        return sexe;
    }  public void total(String s) {
        this.total= s;
    }
    
     public String  total() {
        return total;
    }  public void moyenne(String s) {
        this.moyenne= s;
    }
    
     public String  moyenne() {
        return moyenne;
    }
                                        
   public void classe(String s) {
        this.classe= s;
    }
    
     public String classe() {
        return classe;
    }
 
     public String nom() {
        return nom;
    }

    public void nom(String s) {
        this.nom= s;
    }
    
     public String date() {
        return date;
    }

    public void date(String s) {
        this.date= s;
    }
    
     public String lnaissance() {
        return lnaissance;
    } 
     
    public void lnaissance(String s) {
        this.lnaissance= s;
    }
    
     public String note1() {
        return note1;
    }

    public void note1(String s) {
        this.note1= s;
    }
       public String note2() {
        return note2;
    }

    public void note2(String s) {
        this.note2= s;
    }
       public String note3() {
        return note3;
    }

    public void note3(String s) {
        this.note3= s;
    }
       public String note4() {
        return note4;
    }

    public void note4(String s) {
        this.note4= s;
    }
       public String note5() {
        return note5;
    }

    public void note5(String s) {
        this.note5= s;
    }
       public String note6() {
        return note6;
    }

    public void note6(String s) {
        this.note6= s;
    }
       public String note7() {
        return note7;
    }

    public void note7(String s) {
        this.note7= s;
    }
       public String note8() {
        return note8;
    }

    public void note8(String s) {
        this.note8= s;
    }
       public String note9() {
        return note9;
    }

    public void note9(String s) {
        this.note9= s;
    }   public String note10() {
        return note10;
    }

    public void note10(String s) {
        this.note10= s;
    }
       public String note11() {
        return note11;
    }

    public void note11(String s) {
        this.note11= s;
    }
       public String note12() {
        return note12;
    }

    public void note12(String s) {
        this.note12= s;
    }   public String note13() {
        return note13;
    }

    public void note13(String s) {
        this.note13= s;
    }   public String note14() {
        return note14;
    }

    public void note14(String s) {
        this.note14= s;
    }   public String note15() {
        return note15;
    }

    public void note15(String s) {
        this.note15= s;
    }   public String note16() {
        return note16;
    }

    public void note16(String s) {
        this.note16= s;
    }   public String note17() {
        return note17;
    }

    public void note17(String s) {
        this.note17= s;
    }   public String note18() {
        return note18;
    }

    public void note18(String s) {
        this.note18= s;
    }   public String note19() {
        return note19;
    }

    public void note19(String s) {
        this.note19= s;
    }   public String note20() {
        return note20;
    }

    public void note20(String s) {
        this.note20= s;
    }   public String note21() {
        return note21;
    }

    public void note21(String s) {
        this.note21= s;
    }   public String note22() {
        return note22;
    }

    public void note22(String s) {
        this.note22= s;
    }  public String rang() {
        return rang;
    }

    public void rang(String s) {
        this.rang= s;
    }
    
     
        public String toString1() {
            return "{\"Bulletin de notes\":{" +
                    "\"schoolYear\":" + "\"" + "2018 - 2019" + "\"," +
                    "\"sequence\":" + "\"" + "1" + "\"," +
                    "\"NOM ET PRENOM ELEVE\":" + nom()+ "\n\n" +
                    "\""+memoire.note1+"\":" + note1 + "   " + getGradeLetter("note1") + "\n" +
                    "\""+memoire.note2+"\":" + note2 + "  " + getGradeLetter("note2") + "\n" +
                    "\""+memoire.note3+"\":" + note3 + "   " + getGradeLetter("note3") + "\n" +
                    "\""+memoire.note4+"\":" + note4 + "  " + getGradeLetter("note4") + "\n"+
                    "\""+memoire.note5+"\":" + note5 +"  " + getGradeLetter("note5") + "\n" +
                    "\""+memoire.note6+"\":" + note6 + "  " + getGradeLetter("note6") + "\n" +
                    "\""+memoire.note7+"\":" + note7 + "  " + getGradeLetter("note7") + "\n" +
                    "\""+memoire.note8+"\":" + note8 + "   " + getGradeLetter("note8") + "\n" +
                    "\""+memoire.note9+"\":" + "\"" + getGradeLetter("note9") + "\"," +
                    "\""+memoire.note10+"\":" + "\"" + getGradeLetter("note10") + "\"," +
                    "\""+memoire.note11+"\":" + "\"" + getGradeLetter("note11") + "\"," +
                    "\""+"TOTAL"+"\" : " + "\"" + getGradeLetter("total") + "\"," +
                    "\""+"Moyenne"+"\":" + "\"" + getGradeLetter("total") + "\"," +
                    "\""+"RANG"+"\":" + "\"" + rang() + "\"\n\n\n\n" +
                     
                    "}}";
        }
        private String getGradeLetter(String className) {
            String result;
            switch (className) {
                case "total":
                    result = convertGrade(3);
                    break;case "note1":
                    result = convertGrade(10);
                    break;
                case "moyenne":
                    result = convertGrade(4);
                    break;
                case "note2":
                    result = convertGrade(5);
                    break;
                case "note3":
                    result = convertGrade(8);
                    break;
                case "note4":
                    result = convertGrade(9);
                    break;
                case "note5":
                    result = convertGrade(0);
                    break;
                case "note6":
                    result = convertGrade(00);
                    break;
                case "note7":
                    result = convertGrade(9);
                    break;
                case "note8":
                    result = convertGrade(8);
                    break;
                case "note9":
                    result = convertGrade(5);
                    break;
                case "note10":
                    result = convertGrade(5);
                    break;
                case "note11":
                    result = convertGrade(7);
                    break;case "note12":
                    result = convertGrade(9);
                    break;
                case "note13":
                    result = convertGrade(4);
                    break;
                case "note14":
                    result = convertGrade(8);
                    break;
                    case "note15":
                    result = convertGrade(3);
                    break;
                case "note16":
                    result = convertGrade(Integer.parseInt(note16().trim()));
                    break;
                case "note17":
                    result = convertGrade(Integer.parseInt(note17().trim()));
                    break;
                default:
                    result = "invalid class name";
                    break;
            }
            return result;
        }
        
        private String convertGrade(int grade) {
            String result;
            if (grade == 0 || grade < 3) {
                result = "F";
            } else if (grade > 4 && grade < 6) {
                result = "D";
            } else if (grade > 6 && grade < 8) {
                result = "C";
            } else if (grade > 8 && grade < 9) {
                result = "B";
            } else if (grade > 10 || grade == 14) {
                result = "A";
            } else {
                result = "invalid grade";
            }
            return result;
        }

}
