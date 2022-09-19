/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

/**
 *
 * @author Michelle Wafo
 */
public class ReportCard {
       private final String schoolYear;
        private final String semesterdId;
        private final int studentId;
        private int mathGrade;
        private int englishGrade;
        private int spanishGrade;
        private int historyGrade;
        private int physicsGrade;
        private int biologyGrade;
        private int gymGrade;
        private int artGrade;


        public ReportCard(int studentId, int mathGrade, int englishGrade,
                          int spanishGrade, int historyGrade, int physicsGrade,
                          int biologyGrade, int gymGrade, int artGrade) {
            this.schoolYear = "2016-17";
            this.semesterdId = "Fall";
            this.studentId = studentId;
            this.mathGrade = mathGrade;
            this.englishGrade = englishGrade;
            this.spanishGrade = spanishGrade;
            this.historyGrade = historyGrade;
            this.physicsGrade = physicsGrade;
            this.biologyGrade = biologyGrade;
            this.gymGrade = gymGrade;
            this.artGrade = artGrade;
        }


        public ReportCard(int studentId) {
            this.schoolYear = "2016-17";
            this.semesterdId = "Fall";
            this.studentId = studentId;
        }


        @Override
        public String toString() {
            return "{\"ReportCard\":{" +
                    "\"schoolYear\":" + "\"" + schoolYear + "\"," +
                    "\"semesterdId\":" + "\"" + semesterdId + "\"," +
                    "\"studentId\":" + studentId + "," +
                    "\"mathGrade\":" + mathGrade + "," +
                    "\"englishGrade\":" + englishGrade + "," +
                    "\"spanishGrade\":" + spanishGrade + "," +
                    "\"historyGrade\":" + historyGrade + "," +
                    "\"physicsGrade\":" + physicsGrade + "," +
                    "\"biologyGrade\":" + biologyGrade + "," +
                    "\"gymGrade\":" + gymGrade + "," +
                    "\"artGrade\":" + artGrade + "," +
                    "\"mathGradeLetter\":" + "\"" + getGradeLetter("math") + "\"," +
                    "\"englishGradeLetter\":" + "\"" + getGradeLetter("english") + "\"," +
                    "\"spanishGradeLetter\":" + "\"" + getGradeLetter("spanish") + "\"," +
                    "\"historyGradeLetter\":" + "\"" + getGradeLetter("history") + "\"," +
                    "\"physicsGradeLetter\":" + "\"" + getGradeLetter("physics") + "\"," +
                    "\"biologyGradeLetter\":" + "\"" + getGradeLetter("biology") + "\"," +
                    "\"gymGradeLetter\":" + "\"" + getGradeLetter("gym") + "\"," +
                    "\"artGradeLetter\":" + "\"" + getGradeLetter("art") + "\"" +
                    "}}";
        }


        private String getGradeLetter(String className) {
            String result;
            switch (className) {
                case "math":
                    result = convertGrade(getMathGrade());
                    break;
                case "english":
                    result = convertGrade(getEnglishGrade());
                    break;
                case "spanish":
                    result = convertGrade(getSpanishGrade());
                    break;
                case "history":
                    result = convertGrade(getHistoryGrade());
                    break;
                case "physics":
                    result = convertGrade(getPhysicsGrade());
                    break;
                case "biology":
                    result = convertGrade(getBiologyGrade());
                    break;
                case "gym":
                    result = convertGrade(getGymGrade());
                    break;
                case "art":
                    result = convertGrade(getArtGrade());
                    break;
                default:
                    result = "invalid class name";
                    break;
            }
            return result;
        }

        /**
         * convertGrade
         * Converts a grade number in the range of 0 to 100 into a letter
         * based on a standard grading system
         */
        private String convertGrade(int grade) {
            String result;
            if (grade == 0 || grade < 60) {
                result = "F";
            } else if (grade > 59 && grade < 70) {
                result = "D";
            } else if (grade > 69 && grade < 80) {
                result = "C";
            } else if (grade > 79 && grade < 90) {
                result = "B";
            } else if (grade > 89 || grade == 100) {
                result = "A";
            } else {
                result = "invalid grade";
            }
            return result;
        }

        /*
        Getters - Private methods to get the grade information
        and return it via the API
         */
        private int getMathGrade() {
            return mathGrade;
        }

        private int getEnglishGrade() {
            return englishGrade;
        }

        private int getSpanishGrade() {
            return spanishGrade;
        }

        private int getHistoryGrade() {
            return historyGrade;
        }

        private int getPhysicsGrade() {
            return physicsGrade;
        }

        private int getBiologyGrade() {
            return biologyGrade;
        }

        private int getGymGrade() {
            return gymGrade;
        }

        private int getArtGrade() {
            return artGrade;
        }
    /*
    Setters - Public methods to modify the grade information
     */

        public void setMathGrade(int mathGrade) {
            this.mathGrade = mathGrade;
        }

        public void setEnglishGrade(int englishGrade) {
            this.englishGrade = englishGrade;
        }

        public void setSpanishGrade(int spanishGrade) {
            this.spanishGrade = spanishGrade;
        }

        public void setHistoryGrade(int historyGrade) {
            this.historyGrade = historyGrade;
        }

        public void setPhysicsGrade(int physicsGrade) {
            this.physicsGrade = physicsGrade;
        }

        public void setBiologyGrade(int biologyGrade) {
            this.biologyGrade = biologyGrade;
        }

        public void setGymGrade(int gymGrade) {
            this.gymGrade = gymGrade;
        }

        public void setArtGrade(int artGrade) {
            this.artGrade = artGrade;
}
}