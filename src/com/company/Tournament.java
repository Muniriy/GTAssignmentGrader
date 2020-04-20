package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Tournament {

    /** This method decrements the X of field if it is not null
     * @param x current field state
     * @return updated field state
     */
    private int grassEating(int x) {
        if (x > 0) return --x;
        else return x;
    }

    /** This method calculates the amount of vegetation eaten by the Moose for the current field
     * @param x current field state
     * @return amount of eaten grass
     */
    private double sigmoid(int x) {
        double result = (10 * Math.exp(x)) / (1 + Math.exp(x)) - 5;
        return result;
    }

    /** This method checks if the Player made valid move
     * @param x move of Player should be 1, 2 or 3 for being valid
     * @return true or false
     */
    private boolean isValidMove(int x) {
        return x == 1 || x == 2 || x == 3;
    }

    /** This method trims the ending of the file names from "." till the end
     * @param str file name
     * @return shortened file name
     */
    private String shortenName (String str) {
        return str.substring(0, str.indexOf("."));
    }

    /** This method gets the list of all files in the given directory
     * and puts student files to the String array
     * @param path the directory with files
     * @return the String array with all student file names
     */
    private String[] getAllStudents(String path) {

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String[] studentFiles;

        assert listOfFiles != null;
        studentFiles = new String[listOfFiles.length - 3];
        System.out.println(listOfFiles.length);
        int studentNo = 0;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                System.out.println("File " + listOfFile.getName());
                if ((!listOfFile.getName().equals("Tournament.java")) && (!listOfFile.getName().equals("Player.java")) &&
                        (!listOfFile.getName().equals(".DS_Store"))) studentFiles[studentNo++] = shortenName(listOfFile.getName());
            } else if (listOfFile.isDirectory()) {
                System.out.println("Directory " + listOfFile.getName());
            }
        }
        System.out.println();
        for (String s : studentFiles) System.out.println(s);
        return studentFiles;
    }

    /** This method generates the code of student objects creation.
     * It is used firstly, the outputs have to copied to runTournament() method
     * and then this method can be commented
     * @param classes String array with names of all student classes
     */
    private void generateCode(String[] classes) {
        String[] generatedStrings = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            generatedStrings[i] = classes[i] + " obj" + classes[i] + " = new " + classes[i] + "();\n" +
            "player[" + i + "] = obj" + classes[i] + ";\n";
        }
        for(String s : generatedStrings) System.out.println(s);
    }

    /** This method takes the objects of 2 students' classes and
     * provides the fight between them
     * @param player1 the object of the first player
     * @param player2 the object of the second player
     * @return the scores of both players. Zeroth element is for the first player,
     * first element for the second player
     */
    private double[] pvp(Player player1, Player player2) {
        int rounds = 100;
        int xA = 1;
        int xB = 1;
        int xC = 1;
        int playerOneMove = 0;
        int playerTwoMove = 0;
        int previousPlayerOneMove;
        int previousPlayerTwoMove;
        double[] scores = new double[2];

        for (int i = 0; i < rounds; i++) {

            // Get the previous player moves
            previousPlayerOneMove = playerOneMove;
            previousPlayerTwoMove = playerTwoMove;

//            System.out.println();
//            System.out.println("Round " + i + ": xA = " + xA + " xB = " + xB + " xC = " + xC + "; previous move of P1 is " + previousPlayerOneMove + "; previous move of P2 is " + previousPlayerTwoMove);

            playerOneMove = player1.move(previousPlayerTwoMove, xA, xB, xC);
            if (!isValidMove(playerOneMove)) {
                System.out.println("ERROR: impossible " + player1.getClass() + " move is " + playerOneMove);
                return scores;
            }
            player1.reset();
//            System.out.println("    1st player move is " + playerOneMove);

            playerTwoMove = player2.move(previousPlayerOneMove, xA, xB, xC);
            if (!isValidMove(playerTwoMove)) {
                System.out.println("ERROR: impossible " + player2.getClass() + " move is " + playerTwoMove);
                return scores;
            }
            player2.reset();
//            System.out.println("    2nd player move is " + playerTwoMove);

            if (playerOneMove == playerTwoMove) {
                switch(playerOneMove) {
                    case 1: xA = grassEating(xA);
                        xB++;
                        xC++;
                        break;
                    case 2: xA++;
                        xB = grassEating(xB);
                        xC++;
                        break;
                    case 3: xA++;
                        xB++;
                        xC = grassEating(xC);
                        break;
                    default: break;
                }
            } else {
                boolean aVisited = false;
                boolean bVisited = false;
//                boolean cVisited = false;
                switch (playerOneMove) {
                    case 1: scores[0]+=sigmoid(xA);
                        xA = grassEating(xA);
                        aVisited = true;
                        break;
                    case 2: scores[0]+=sigmoid(xB);
                        xB = grassEating(xB);
                        bVisited = true;
                        break;
                    case 3: scores[0]+=sigmoid(xC);
                        xC = grassEating(xC);
//                        cVisited = true;
                        break;
                    default: System.out.println("Something went wrong. Impossible move was returned by " + player1.getClass());
                        break;
                }
                switch (playerTwoMove) {
                    case 1: scores[1]+=sigmoid(xA);
                        xA = grassEating(xA);
                        aVisited = true;
                        break;
                    case 2: scores[1]+=sigmoid(xB);
                        xB = grassEating(xB);
                        bVisited = true;
                        break;
                    case 3: scores[1]+=sigmoid(xC);
                        xC = grassEating(xC);
//                        cVisited = true;
                        break;
                    default: System.out.println("Something went wrong. Impossible move was returned by " + player2.getClass());
                        break;
                }
                if (!aVisited) xA++;
                else if (!bVisited) xB++;
                else xC++;
            }
//            System.out.println("Score of " + player1.getClass() + " is " + scores[0]);
//            System.out.println("Score of " + player2.getClass() + " is " + scores[1]);
        }
        return scores;
    }

    /** This method runs the tournament between all players.
     * Before running it, run generateCode() method and paste
     * the outputs inside this method
     * @param classes String array with names of all student classes
     */
    private void runTournament(String[] classes) {

        double[] scores = new double[classes.length];
        String[] emails = new String[classes.length];
        Player[] player = new Player[classes.length];

//        {
//            AydarGabdrahmanovCode objAydarGabdrahmanovCode = new AydarGabdrahmanovCode();
//            player[0] = objAydarGabdrahmanovCode;
//
//            EgorKlementevCode objEgorKlementevCode = new EgorKlementevCode();
//            player[1] = objEgorKlementevCode;
//
//            ElviraSalikhovaCode objElviraSalikhovaCode = new ElviraSalikhovaCode();
//            player[2] = objElviraSalikhovaCode;
//
//            RimRakhimovCode objRimRakhimovCode = new RimRakhimovCode();
//            player[3] = objRimRakhimovCode;
//
//            MikhailTkachenkoCode objMikhailTkachenkoCode = new MikhailTkachenkoCode();
//            player[4] = objMikhailTkachenkoCode;
//
//            RobertoChavezCode objRobertoChavezCode = new RobertoChavezCode();
//            player[5] = objRobertoChavezCode;
//
//            MaximSaloCode objMaximSaloCode = new MaximSaloCode();
//            player[6] = objMaximSaloCode;
//
//            IlyaAlonovCode objIlyaAlonovCode = new IlyaAlonovCode();
//            player[7] = objIlyaAlonovCode;
//
//            EgorBaklanovCode objEgorBaklanovCode = new EgorBaklanovCode();
//            player[8] = objEgorBaklanovCode;
//
//            AnastassiyaBoikoCode objAnastassiyaBoikoCode = new AnastassiyaBoikoCode();
//            player[9] = objAnastassiyaBoikoCode;
//
//            TimerlanNasyrovCode objTimerlanNasyrovCode = new TimerlanNasyrovCode();
//            player[10] = objTimerlanNasyrovCode;
//
//            VadimSolovovCode objVadimSolovovCode = new VadimSolovovCode();
//            player[11] = objVadimSolovovCode;
//
//            ArtemKozlovCode objArtemKozlovCode = new ArtemKozlovCode();
//            player[12] = objArtemKozlovCode;
//
//            NiyazFahretdinovCode objNiyazFahretdinovCode = new NiyazFahretdinovCode();
//            player[13] = objNiyazFahretdinovCode;
//
//            VladislavBakhteevCode objVladislavBakhteevCode = new VladislavBakhteevCode();
//            player[14] = objVladislavBakhteevCode;
//
//            RishatMaksudovCode objRishatMaksudovCode = new RishatMaksudovCode();
//            player[15] = objRishatMaksudovCode;
//
//            OydinoyZufarovaCode objOydinoyZufarovaCode = new OydinoyZufarovaCode();
//            player[16] = objOydinoyZufarovaCode;
//
//            OlegAndriyanchenkoCode objOlegAndriyanchenkoCode = new OlegAndriyanchenkoCode();
//            player[17] = objOlegAndriyanchenkoCode;
//
//            KamilyaTimchenkoCode objKamilyaTimchenkoCode = new KamilyaTimchenkoCode();
//            player[18] = objKamilyaTimchenkoCode;
//
//            AmirSubaevCode objAmirSubaevCode = new AmirSubaevCode();
//            player[19] = objAmirSubaevCode;
//
//            NikitaNigmatullinCode objNikitaNigmatullinCode = new NikitaNigmatullinCode();
//            player[20] = objNikitaNigmatullinCode;
//
//            PeterZakharkinCode objPeterZakharkinCode = new PeterZakharkinCode();
//            player[21] = objPeterZakharkinCode;
//
//            NikolaNovarlicCode objNikolaNovarlicCode = new NikolaNovarlicCode();
//            player[22] = objNikolaNovarlicCode;
//
//            FaritGaleevCode objFaritGaleevCode = new FaritGaleevCode();
//            player[23] = objFaritGaleevCode;
//
//            AlexanderGrukCode objAlexanderGrukCode = new AlexanderGrukCode();
//            player[24] = objAlexanderGrukCode;
//
//            DenisLevkovetsCode objDenisLevkovetsCode = new DenisLevkovetsCode();
//            player[25] = objDenisLevkovetsCode;
//
//            DanilKabirovCode objDanilKabirovCode = new DanilKabirovCode();
//            player[26] = objDanilKabirovCode;
//
//            DanilaKochanCode objDanilaKochanCode = new DanilaKochanCode();
//            player[27] = objDanilaKochanCode;
//
//            KamilAlimovCode objKamilAlimovCode = new KamilAlimovCode();
//            player[28] = objKamilAlimovCode;
//
//            ArtemIurinCode objArtemIurinCode = new ArtemIurinCode();
//            player[29] = objArtemIurinCode;
//
//            AlbertBadretdinovCode objAlbertBadretdinovCode = new AlbertBadretdinovCode();
//            player[30] = objAlbertBadretdinovCode;
//
//            RuslanSabirovCode objRuslanSabirovCode = new RuslanSabirovCode();
//            player[31] = objRuslanSabirovCode;
//
//            NikolaiMikriukovCode objNikolaiMikriukovCode = new NikolaiMikriukovCode();
//            player[32] = objNikolaiMikriukovCode;
//
//            NikitaKostenkoCode objNikitaKostenkoCode = new NikitaKostenkoCode();
//            player[33] = objNikitaKostenkoCode;
//
//            ArturRakhmatullinCode objArturRakhmatullinCode = new ArturRakhmatullinCode();
//            player[34] = objArturRakhmatullinCode;
//
//            KusalKcCode objKusalKcCode = new KusalKcCode();
//            player[35] = objKusalKcCode;
//
//            RamilAskarovCode objRamilAskarovCode = new RamilAskarovCode();
//            player[36] = objRamilAskarovCode;
//
//            KamilSaitovCode objKamilSaitovCode = new KamilSaitovCode();
//            player[37] = objKamilSaitovCode;
//
//            DmitryKonevCode objDmitryKonevCode = new DmitryKonevCode();
//            player[38] = objDmitryKonevCode;
//
//            ValeriyaVertashCode objValeriyaVertashCode = new ValeriyaVertashCode();
//            player[39] = objValeriyaVertashCode;
//
//            TemurKholmatovCode objTemurKholmatovCode = new TemurKholmatovCode();
//            player[40] = objTemurKholmatovCode;
//
//            ArinaFedorovskayaCode objArinaFedorovskayaCode = new ArinaFedorovskayaCode();
//            player[41] = objArinaFedorovskayaCode;
//
//            VladislavSavchukCode objVladislavSavchukCode = new VladislavSavchukCode();
//            player[42] = objVladislavSavchukCode;
//
//            KamilAkhmetovCode objKamilAkhmetovCode = new KamilAkhmetovCode();
//            player[43] = objKamilAkhmetovCode;
//
//            IliaMazanCode objIliaMazanCode = new IliaMazanCode();
//            player[44] = objIliaMazanCode;
//
//            AlexeyLogachevCode objAlexeyLogachevCode = new AlexeyLogachevCode();
//            player[45] = objAlexeyLogachevCode;
//
//            AntonKrylovCode objAntonKrylovCode = new AntonKrylovCode();
//            player[46] = objAntonKrylovCode;
//
//            DragosStrugarCode objDragosStrugarCode = new DragosStrugarCode();
//            player[47] = objDragosStrugarCode;
//
//            DmitryKochetovCode objDmitryKochetovCode = new DmitryKochetovCode();
//            player[48] = objDmitryKochetovCode;
//
//            IskenderGuseynovCode objIskenderGuseynovCode = new IskenderGuseynovCode();
//            player[49] = objIskenderGuseynovCode;
//
//            DariaVaskovskayaCode objDariaVaskovskayaCode = new DariaVaskovskayaCode();
//            player[50] = objDariaVaskovskayaCode;
//
//            EvgenyRomanovCode objEvgenyRomanovCode = new EvgenyRomanovCode();
//            player[51] = objEvgenyRomanovCode;
//
//            MaximPopovCode objMaximPopovCode = new MaximPopovCode();
//            player[52] = objMaximPopovCode;
//
//            AleksandraSedovaCode objAleksandraSedovaCode = new AleksandraSedovaCode();
//            player[53] = objAleksandraSedovaCode;
//
//            SalavatDinmuhametovCode objSalavatDinmuhametovCode = new SalavatDinmuhametovCode();
//            player[54] = objSalavatDinmuhametovCode;
//
//            RinatMullahmetovCode objRinatMullahmetovCode = new RinatMullahmetovCode();
//            player[55] = objRinatMullahmetovCode;
//
//            LeonidLyginCode objLeonidLyginCode = new LeonidLyginCode();
//            player[56] = objLeonidLyginCode;
//
//            RomanBogachevCode objRomanBogachevCode = new RomanBogachevCode();
//            player[57] = objRomanBogachevCode;
//
//            MikhailLyametsCode objMikhailLyametsCode = new MikhailLyametsCode();
//            player[58] = objMikhailLyametsCode;
//
//            PavelNikulinCode objPavelNikulinCode = new PavelNikulinCode();
//            player[59] = objPavelNikulinCode;
//
//            FarhadKhakimovCode objFarhadKhakimovCode = new FarhadKhakimovCode();
//            player[60] = objFarhadKhakimovCode;
//
//            HussainKaraFallahCode objHussainKaraFallahCode = new HussainKaraFallahCode();
//            player[61] = objHussainKaraFallahCode;
//
//            ArtemiiBykovCode objArtemiiBykovCode = new ArtemiiBykovCode();
//            player[62] = objArtemiiBykovCode;
//
//            AlexanderAndryukovCode objAlexanderAndryukovCode = new AlexanderAndryukovCode();
//            player[63] = objAlexanderAndryukovCode;
//
//            ElenaPatrushevaCode objElenaPatrushevaCode = new ElenaPatrushevaCode();
//            player[64] = objElenaPatrushevaCode;
//
//            AmadeyKuspakovCode objAmadeyKuspakovCode = new AmadeyKuspakovCode();
//            player[65] = objAmadeyKuspakovCode;
//
//            IlshatFatkhullinCode objIlshatFatkhullinCode = new IlshatFatkhullinCode();
//            player[66] = objIlshatFatkhullinCode;
//
//            AbdurasulRakhimovCode objAbdurasulRakhimovCode = new AbdurasulRakhimovCode();
//            player[67] = objAbdurasulRakhimovCode;
//
//            EvgeniyMuravevCode objEvgeniyMuravevCode = new EvgeniyMuravevCode();
//            player[68] = objEvgeniyMuravevCode;
//
//            MaximBurovCode objMaximBurovCode = new MaximBurovCode();
//            player[69] = objMaximBurovCode;
//
//            EnesAyanCode objEnesAyanCode = new EnesAyanCode();
//            player[70] = objEnesAyanCode;
//
//            SusannaGimaevaCode objSusannaGimaevaCode = new SusannaGimaevaCode();
//            player[71] = objSusannaGimaevaCode;
//
//            EvgeniaKivotovaCode objEvgeniaKivotovaCode = new EvgeniaKivotovaCode();
//            player[72] = objEvgeniaKivotovaCode;
//
//            AliyaZagidullinaCode objAliyaZagidullinaCode = new AliyaZagidullinaCode();
//            player[73] = objAliyaZagidullinaCode;
//
//            IlnurMamedbakovCode objIlnurMamedbakovCode = new IlnurMamedbakovCode();
//            player[74] = objIlnurMamedbakovCode;
//
//            DenisPimenovCode objDenisPimenovCode = new DenisPimenovCode();
//            player[75] = objDenisPimenovCode;
//
//            RinatBabichevCode objRinatBabichevCode = new RinatBabichevCode();
//            player[76] = objRinatBabichevCode;
//
//            DanisBegishevCode objDanisBegishevCode = new DanisBegishevCode();
//            player[77] = objDanisBegishevCode;
//
//            AlekseiZhuchkovCode objAlekseiZhuchkovCode = new AlekseiZhuchkovCode();
//            player[78] = objAlekseiZhuchkovCode;
//
//            ElizavetaKolchanovaCode objElizavetaKolchanovaCode = new ElizavetaKolchanovaCode();
//            player[79] = objElizavetaKolchanovaCode;
//
//            DaniilShilintsevCode objDaniilShilintsevCode = new DaniilShilintsevCode();
//            player[80] = objDaniilShilintsevCode;
//
//            VyacheslavGoreevCode objVyacheslavGoreevCode = new VyacheslavGoreevCode();
//            player[81] = objVyacheslavGoreevCode;
//
//            IliaProkopevCode objIliaProkopevCode = new IliaProkopevCode();
//            player[82] = objIliaProkopevCode;
//
//            DanilKalininCode objDanilKalininCode = new DanilKalininCode();
//            player[83] = objDanilKalininCode;
//
//            AnastasiiaGromovaCode objAnastasiiaGromovaCode = new AnastasiiaGromovaCode();
//            player[84] = objAnastasiiaGromovaCode;
//
//            MariiaCharikovaCode objMariiaCharikovaCode = new MariiaCharikovaCode();
//            player[85] = objMariiaCharikovaCode;
//
//            VyacheslavVasilevCode objVyacheslavVasilevCode = new VyacheslavVasilevCode();
//            player[86] = objVyacheslavVasilevCode;
//
//            EugeneBondarevCode objEugeneBondarevCode = new EugeneBondarevCode();
//            player[87] = objEugeneBondarevCode;
//
//            JafarBadourCode objJafarBadourCode = new JafarBadourCode();
//            player[88] = objJafarBadourCode;
//
//            MikhailMoiseevCode objMikhailMoiseevCode = new MikhailMoiseevCode();
//            player[89] = objMikhailMoiseevCode;
//
//            MadinaGafarovaCode objMadinaGafarovaCode = new MadinaGafarovaCode();
//            player[90] = objMadinaGafarovaCode;
//
//            SemenKiselevCode objSemenKiselevCode = new SemenKiselevCode();
//            player[91] = objSemenKiselevCode;
//
//            GlebPetrakovCode objGlebPetrakovCode = new GlebPetrakovCode();
//            player[92] = objGlebPetrakovCode;
//
//            SyedAbbasCode objSyedAbbasCode = new SyedAbbasCode();
//            player[93] = objSyedAbbasCode;
//
//            VladislavSmirnovCode objVladislavSmirnovCode = new VladislavSmirnovCode();
//            player[94] = objVladislavSmirnovCode;
//
//            DanyilDvorianovCode objDanyilDvorianovCode = new DanyilDvorianovCode();
//            player[95] = objDanyilDvorianovCode;
//
//            AndreyChertkovCode objAndreyChertkovCode = new AndreyChertkovCode();
//            player[96] = objAndreyChertkovCode;
//
//            LenarGumerovCode objLenarGumerovCode = new LenarGumerovCode();
//            player[97] = objLenarGumerovCode;
//
//            IrekNazmievCode objIrekNazmievCode = new IrekNazmievCode();
//            player[98] = objIrekNazmievCode;
//
//            SvyatoslavSemenyukCode objSvyatoslavSemenyukCode = new SvyatoslavSemenyukCode();
//            player[99] = objSvyatoslavSemenyukCode;
//
//            IlnurGaripovCode objIlnurGaripovCode = new IlnurGaripovCode();
//            player[100] = objIlnurGaripovCode;
//
//            IgorVakhulaCode objIgorVakhulaCode = new IgorVakhulaCode();
//            player[101] = objIgorVakhulaCode;
//
//            SvetlanaKabirovaCode objSvetlanaKabirovaCode = new SvetlanaKabirovaCode();
//            player[102] = objSvetlanaKabirovaCode;
//
//            RomanSolovevCode objRomanSolovevCode = new RomanSolovevCode();
//            player[103] = objRomanSolovevCode;
//
//            RuslanShakirovCode objRuslanShakirovCode = new RuslanShakirovCode();
//            player[104] = objRuslanShakirovCode;
//
//            PavelVybornovCode objPavelVybornovCode = new PavelVybornovCode();
//            player[105] = objPavelVybornovCode;
//
//            AntonTimchenkoCode objAntonTimchenkoCode = new AntonTimchenkoCode();
//            player[106] = objAntonTimchenkoCode;
//
//            LiliyaGabdrahimovaCode objLiliyaGabdrahimovaCode = new LiliyaGabdrahimovaCode();
//            player[107] = objLiliyaGabdrahimovaCode;
//
//            AhmedElBatanonyCode objAhmedElBatanonyCode = new AhmedElBatanonyCode();
//            player[108] = objAhmedElBatanonyCode;
//
//            BogdanFedotovCode objBogdanFedotovCode = new BogdanFedotovCode();
//            player[109] = objBogdanFedotovCode;
//
//            AbdulkhamidMuminovCode objAbdulkhamidMuminovCode = new AbdulkhamidMuminovCode();
//            player[110] = objAbdulkhamidMuminovCode;
//
//            AygulMalikovaCode objAygulMalikovaCode = new AygulMalikovaCode();
//            player[111] = objAygulMalikovaCode;
//
//        }

        {
            AydarGabdrahmanovCode objAydarGabdrahmanovCode = new AydarGabdrahmanovCode();
            player[0] = objAydarGabdrahmanovCode;

            EgorKlementevCode objEgorKlementevCode = new EgorKlementevCode();
            player[1] = objEgorKlementevCode;

            ElviraSalikhovaCode objElviraSalikhovaCode = new ElviraSalikhovaCode();
            player[2] = objElviraSalikhovaCode;

            RimRakhimovCode objRimRakhimovCode = new RimRakhimovCode();
            player[3] = objRimRakhimovCode;

            MikhailTkachenkoCode objMikhailTkachenkoCode = new MikhailTkachenkoCode();
            player[4] = objMikhailTkachenkoCode;

            RobertoChavezCode objRobertoChavezCode = new RobertoChavezCode();
            player[5] = objRobertoChavezCode;

            MaximSaloCode objMaximSaloCode = new MaximSaloCode();
            player[6] = objMaximSaloCode;

            IlyaAlonovCode objIlyaAlonovCode = new IlyaAlonovCode();
            player[7] = objIlyaAlonovCode;

            EgorBaklanovCode objEgorBaklanovCode = new EgorBaklanovCode();
            player[8] = objEgorBaklanovCode;

            AnastassiyaBoikoCode objAnastassiyaBoikoCode = new AnastassiyaBoikoCode();
            player[9] = objAnastassiyaBoikoCode;

            TimerlanNasyrovCode objTimerlanNasyrovCode = new TimerlanNasyrovCode();
            player[10] = objTimerlanNasyrovCode;

            VadimSolovovCode objVadimSolovovCode = new VadimSolovovCode();
            player[11] = objVadimSolovovCode;

            ArtemKozlovCode objArtemKozlovCode = new ArtemKozlovCode();
            player[12] = objArtemKozlovCode;

            NiyazFahretdinovCode objNiyazFahretdinovCode = new NiyazFahretdinovCode();
            player[13] = objNiyazFahretdinovCode;

            VladislavBakhteevCode objVladislavBakhteevCode = new VladislavBakhteevCode();
            player[14] = objVladislavBakhteevCode;

            RishatMaksudovCode objRishatMaksudovCode = new RishatMaksudovCode();
            player[15] = objRishatMaksudovCode;

            OydinoyZufarovaCode objOydinoyZufarovaCode = new OydinoyZufarovaCode();
            player[16] = objOydinoyZufarovaCode;

            OlegAndriyanchenkoCode objOlegAndriyanchenkoCode = new OlegAndriyanchenkoCode();
            player[17] = objOlegAndriyanchenkoCode;

            KamilyaTimchenkoCode objKamilyaTimchenkoCode = new KamilyaTimchenkoCode();
            player[18] = objKamilyaTimchenkoCode;

            AmirSubaevCode objAmirSubaevCode = new AmirSubaevCode();
            player[19] = objAmirSubaevCode;

            NikitaNigmatullinCode objNikitaNigmatullinCode = new NikitaNigmatullinCode();
            player[20] = objNikitaNigmatullinCode;

            PeterZakharkinCode objPeterZakharkinCode = new PeterZakharkinCode();
            player[21] = objPeterZakharkinCode;

            NikolaNovarlicCode objNikolaNovarlicCode = new NikolaNovarlicCode();
            player[22] = objNikolaNovarlicCode;

            FaritGaleevCode objFaritGaleevCode = new FaritGaleevCode();
            player[23] = objFaritGaleevCode;

            AlexanderGrukCode objAlexanderGrukCode = new AlexanderGrukCode();
            player[24] = objAlexanderGrukCode;

            DenisLevkovetsCode objDenisLevkovetsCode = new DenisLevkovetsCode();
            player[25] = objDenisLevkovetsCode;

            DanilKabirovCode objDanilKabirovCode = new DanilKabirovCode();
            player[26] = objDanilKabirovCode;

            DanilaKochanCode objDanilaKochanCode = new DanilaKochanCode();
            player[27] = objDanilaKochanCode;

            KamilAlimovCode objKamilAlimovCode = new KamilAlimovCode();
            player[28] = objKamilAlimovCode;

            ArtemIurinCode objArtemIurinCode = new ArtemIurinCode();
            player[29] = objArtemIurinCode;

            AlbertBadretdinovCode objAlbertBadretdinovCode = new AlbertBadretdinovCode();
            player[30] = objAlbertBadretdinovCode;

            RuslanSabirovCode objRuslanSabirovCode = new RuslanSabirovCode();
            player[31] = objRuslanSabirovCode;

            NikolaiMikriukovCode objNikolaiMikriukovCode = new NikolaiMikriukovCode();
            player[32] = objNikolaiMikriukovCode;

            NikitaKostenkoCode objNikitaKostenkoCode = new NikitaKostenkoCode();
            player[33] = objNikitaKostenkoCode;

            ArturRakhmatullinCode objArturRakhmatullinCode = new ArturRakhmatullinCode();
            player[34] = objArturRakhmatullinCode;

            KusalKcCode objKusalKcCode = new KusalKcCode();
            player[35] = objKusalKcCode;

            RamilAskarovCode objRamilAskarovCode = new RamilAskarovCode();
            player[36] = objRamilAskarovCode;

            KamilSaitovCode objKamilSaitovCode = new KamilSaitovCode();
            player[37] = objKamilSaitovCode;

            DmitryKonevCode objDmitryKonevCode = new DmitryKonevCode();
            player[38] = objDmitryKonevCode;

            ValeriyaVertashCode objValeriyaVertashCode = new ValeriyaVertashCode();
            player[39] = objValeriyaVertashCode;

            TemurKholmatovCode objTemurKholmatovCode = new TemurKholmatovCode();
            player[40] = objTemurKholmatovCode;

            ArinaFedorovskayaCode objArinaFedorovskayaCode = new ArinaFedorovskayaCode();
            player[41] = objArinaFedorovskayaCode;

            VladislavSavchukCode objVladislavSavchukCode = new VladislavSavchukCode();
            player[42] = objVladislavSavchukCode;

            KamilAkhmetovCode objKamilAkhmetovCode = new KamilAkhmetovCode();
            player[43] = objKamilAkhmetovCode;

            IliaMazanCode objIliaMazanCode = new IliaMazanCode();
            player[44] = objIliaMazanCode;

            AlexeyLogachevCode objAlexeyLogachevCode = new AlexeyLogachevCode();
            player[45] = objAlexeyLogachevCode;

            AntonKrylovCode objAntonKrylovCode = new AntonKrylovCode();
            player[46] = objAntonKrylovCode;

            DragosStrugarCode objDragosStrugarCode = new DragosStrugarCode();
            player[47] = objDragosStrugarCode;

            DmitryKochetovCode objDmitryKochetovCode = new DmitryKochetovCode();
            player[48] = objDmitryKochetovCode;

            IskenderGuseynovCode objIskenderGuseynovCode = new IskenderGuseynovCode();
            player[49] = objIskenderGuseynovCode;

            DariaVaskovskayaCode objDariaVaskovskayaCode = new DariaVaskovskayaCode();
            player[50] = objDariaVaskovskayaCode;

            EvgenyRomanovCode objEvgenyRomanovCode = new EvgenyRomanovCode();
            player[51] = objEvgenyRomanovCode;

            MaximPopovCode objMaximPopovCode = new MaximPopovCode();
            player[52] = objMaximPopovCode;

            AleksandraSedovaCode objAleksandraSedovaCode = new AleksandraSedovaCode();
            player[53] = objAleksandraSedovaCode;

            SalavatDinmuhametovCode objSalavatDinmuhametovCode = new SalavatDinmuhametovCode();
            player[54] = objSalavatDinmuhametovCode;

            RinatMullahmetovCode objRinatMullahmetovCode = new RinatMullahmetovCode();
            player[55] = objRinatMullahmetovCode;

            LeonidLyginCode objLeonidLyginCode = new LeonidLyginCode();
            player[56] = objLeonidLyginCode;

            RomanBogachevCode objRomanBogachevCode = new RomanBogachevCode();
            player[57] = objRomanBogachevCode;

            MikhailLyametsCode objMikhailLyametsCode = new MikhailLyametsCode();
            player[58] = objMikhailLyametsCode;

            PavelNikulinCode objPavelNikulinCode = new PavelNikulinCode();
            player[59] = objPavelNikulinCode;

            FarhadKhakimovCode objFarhadKhakimovCode = new FarhadKhakimovCode();
            player[60] = objFarhadKhakimovCode;

            HussainKaraFallahCode objHussainKaraFallahCode = new HussainKaraFallahCode();
            player[61] = objHussainKaraFallahCode;

            ArtemiiBykovCode objArtemiiBykovCode = new ArtemiiBykovCode();
            player[62] = objArtemiiBykovCode;

            AlexanderAndryukovCode objAlexanderAndryukovCode = new AlexanderAndryukovCode();
            player[63] = objAlexanderAndryukovCode;

            ElenaPatrushevaCode objElenaPatrushevaCode = new ElenaPatrushevaCode();
            player[64] = objElenaPatrushevaCode;

            AmadeyKuspakovCode objAmadeyKuspakovCode = new AmadeyKuspakovCode();
            player[65] = objAmadeyKuspakovCode;

            IlshatFatkhullinCode objIlshatFatkhullinCode = new IlshatFatkhullinCode();
            player[66] = objIlshatFatkhullinCode;

            AbdurasulRakhimovCode objAbdurasulRakhimovCode = new AbdurasulRakhimovCode();
            player[67] = objAbdurasulRakhimovCode;

            EvgeniyMuravevCode objEvgeniyMuravevCode = new EvgeniyMuravevCode();
            player[68] = objEvgeniyMuravevCode;

            MaximBurovCode objMaximBurovCode = new MaximBurovCode();
            player[69] = objMaximBurovCode;

            EnesAyanCode objEnesAyanCode = new EnesAyanCode();
            player[70] = objEnesAyanCode;

            SusannaGimaevaCode objSusannaGimaevaCode = new SusannaGimaevaCode();
            player[71] = objSusannaGimaevaCode;

            EvgeniaKivotovaCode objEvgeniaKivotovaCode = new EvgeniaKivotovaCode();
            player[72] = objEvgeniaKivotovaCode;

            AliyaZagidullinaCode objAliyaZagidullinaCode = new AliyaZagidullinaCode();
            player[73] = objAliyaZagidullinaCode;

            IlnurMamedbakovCode objIlnurMamedbakovCode = new IlnurMamedbakovCode();
            player[74] = objIlnurMamedbakovCode;

            DenisPimenovCode objDenisPimenovCode = new DenisPimenovCode();
            player[75] = objDenisPimenovCode;

            RinatBabichevCode objRinatBabichevCode = new RinatBabichevCode();
            player[76] = objRinatBabichevCode;

            DanisBegishevCode objDanisBegishevCode = new DanisBegishevCode();
            player[77] = objDanisBegishevCode;

            EgorPolivtsevCode objEgorPolivtsevCode = new EgorPolivtsevCode();
            player[78] = objEgorPolivtsevCode;

            AlekseiZhuchkovCode objAlekseiZhuchkovCode = new AlekseiZhuchkovCode();
            player[79] = objAlekseiZhuchkovCode;

            ElizavetaKolchanovaCode objElizavetaKolchanovaCode = new ElizavetaKolchanovaCode();
            player[80] = objElizavetaKolchanovaCode;

            DaniilShilintsevCode objDaniilShilintsevCode = new DaniilShilintsevCode();
            player[81] = objDaniilShilintsevCode;

            VyacheslavGoreevCode objVyacheslavGoreevCode = new VyacheslavGoreevCode();
            player[82] = objVyacheslavGoreevCode;

            IliaProkopevCode objIliaProkopevCode = new IliaProkopevCode();
            player[83] = objIliaProkopevCode;

            DanilKalininCode objDanilKalininCode = new DanilKalininCode();
            player[84] = objDanilKalininCode;

            AnastasiiaGromovaCode objAnastasiiaGromovaCode = new AnastasiiaGromovaCode();
            player[85] = objAnastasiiaGromovaCode;

            MariiaCharikovaCode objMariiaCharikovaCode = new MariiaCharikovaCode();
            player[86] = objMariiaCharikovaCode;

            VyacheslavVasilevCode objVyacheslavVasilevCode = new VyacheslavVasilevCode();
            player[87] = objVyacheslavVasilevCode;

            EugeneBondarevCode objEugeneBondarevCode = new EugeneBondarevCode();
            player[88] = objEugeneBondarevCode;

            JafarBadourCode objJafarBadourCode = new JafarBadourCode();
            player[89] = objJafarBadourCode;

            MikhailMoiseevCode objMikhailMoiseevCode = new MikhailMoiseevCode();
            player[90] = objMikhailMoiseevCode;

            MadinaGafarovaCode objMadinaGafarovaCode = new MadinaGafarovaCode();
            player[91] = objMadinaGafarovaCode;

            SemenKiselevCode objSemenKiselevCode = new SemenKiselevCode();
            player[92] = objSemenKiselevCode;

            GlebPetrakovCode objGlebPetrakovCode = new GlebPetrakovCode();
            player[93] = objGlebPetrakovCode;

            SyedAbbasCode objSyedAbbasCode = new SyedAbbasCode();
            player[94] = objSyedAbbasCode;

            VladislavSmirnovCode objVladislavSmirnovCode = new VladislavSmirnovCode();
            player[95] = objVladislavSmirnovCode;

            DanyilDvorianovCode objDanyilDvorianovCode = new DanyilDvorianovCode();
            player[96] = objDanyilDvorianovCode;

            AndreyChertkovCode objAndreyChertkovCode = new AndreyChertkovCode();
            player[97] = objAndreyChertkovCode;

            LenarGumerovCode objLenarGumerovCode = new LenarGumerovCode();
            player[98] = objLenarGumerovCode;

            IrekNazmievCode objIrekNazmievCode = new IrekNazmievCode();
            player[99] = objIrekNazmievCode;

            SvyatoslavSemenyukCode objSvyatoslavSemenyukCode = new SvyatoslavSemenyukCode();
            player[100] = objSvyatoslavSemenyukCode;

            IlnurGaripovCode objIlnurGaripovCode = new IlnurGaripovCode();
            player[101] = objIlnurGaripovCode;

            IgorVakhulaCode objIgorVakhulaCode = new IgorVakhulaCode();
            player[102] = objIgorVakhulaCode;

            SvetlanaKabirovaCode objSvetlanaKabirovaCode = new SvetlanaKabirovaCode();
            player[103] = objSvetlanaKabirovaCode;

            RomanSolovevCode objRomanSolovevCode = new RomanSolovevCode();
            player[104] = objRomanSolovevCode;

            RuslanShakirovCode objRuslanShakirovCode = new RuslanShakirovCode();
            player[105] = objRuslanShakirovCode;

            PavelVybornovCode objPavelVybornovCode = new PavelVybornovCode();
            player[106] = objPavelVybornovCode;

            AntonTimchenkoCode objAntonTimchenkoCode = new AntonTimchenkoCode();
            player[107] = objAntonTimchenkoCode;

            LiliyaGabdrahimovaCode objLiliyaGabdrahimovaCode = new LiliyaGabdrahimovaCode();
            player[108] = objLiliyaGabdrahimovaCode;

            AhmedElBatanonyCode objAhmedElBatanonyCode = new AhmedElBatanonyCode();
            player[109] = objAhmedElBatanonyCode;

            BogdanFedotovCode objBogdanFedotovCode = new BogdanFedotovCode();
            player[110] = objBogdanFedotovCode;

            AbdulkhamidMuminovCode objAbdulkhamidMuminovCode = new AbdulkhamidMuminovCode();
            player[111] = objAbdulkhamidMuminovCode;

            AygulMalikovaCode objAygulMalikovaCode = new AygulMalikovaCode();
            player[112] = objAygulMalikovaCode;

        }

        for (int i = 0; i < classes.length; i++) {
            emails[i] = player[i].getEmail();
//            if (i == 0 || i == 17 || i == 18 || i == 22 || i == 45 || i == 58 || i == 81 || i == 116) continue;
            for (int j = i + 1; j < classes.length; j++) {
//                if (j == 0 || j == 17 || j == 18 || j == 22 || j == 45 || j == 58 || j == 81 || j == 116) continue;
                System.out.println();
                System.out.println("Fight between " + player[i].getClass() + "(" + i + ") and " + player[j].getClass() + "(" + j + ")");
                double[] pvpGrades = pvp(player[i], player[j]);
                scores[i] += pvpGrades[0];
                scores[j] += pvpGrades[1];
                System.out.println(player[i].getClass() + " got " + pvpGrades[0] + ", and " + player[j].getClass() + " got " + pvpGrades[1]);
            }
        }

        System.out.println();
        for (int i = 0; i < classes.length; i++) System.out.println(emails[i] + " " + scores[i]);
        generateGradesFile(emails, scores);
    }

    /** This method generates the file grades.csv with student grades
     * @param emails String array with all student emails
     * @param scores array with all scores
     */
    private void generateGradesFile(String[] emails, double[] scores) {
        try {
            FileWriter csvWriter = new FileWriter("grades.csv");
            csvWriter.flush();
            csvWriter.append("#");
            csvWriter.append(",");
            csvWriter.append("Email");
            csvWriter.append(",");
            csvWriter.append("Grade");
            csvWriter.append("\n");

            for (int i = 0; i < emails.length; i++) {
                csvWriter.append(i + "");
                csvWriter.append(",");
                csvWriter.append(emails[i] + "");
                csvWriter.append(",");
                csvWriter.append(scores[i] + "");
                csvWriter.append("\n");
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** Entry point of the program
     * @param args arguments of the program
     */
    public static void main(String[] args) {
        String path = "./src/com/company";
        String[] studentClasses;
        Tournament tournament = new Tournament();
        studentClasses = tournament.getAllStudents(path);

        // Uncomment firstly to get list the generated code and copy to  and then comment
//        tournament.generateCode(studentClasses);

        // Comment firstly and then uncomment to run the program
        tournament.runTournament(studentClasses);
    }
}

