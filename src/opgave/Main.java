package opgave;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//Generel egen note til hvordan filer kan læses og hvordan man benytter lambda udtryk på data
public class Main {

    public static void main(String[] args) {

        //tekst streng til filen
        String filePath = Paths.get("src/","opgave/Dat20Bstuderende.csv").toString();

        //Forsikring om at fil placering bliver opfanget/findes
        System.out.println(filePath);

        Main main = new Main();

        main.parseCSV(filePath);

    }

    //Metode til læse CSV filen
    public void parseCSV(String filePath){

        //Regex pattern streng
        String regex = "(?<USERNAME>[a-z@.0-9]{20}?);(?<FIRSTNAME>[A-Za-z æøåÆØÅé]{0,30} ?);(?<LASTNAME>[A-Za-z æøåÆØÅ-]{0,30} ?);(?<CLASSORIGIN>[A-Z ]{0,1} ?)";

        //Regex compiler
        //Tager og omdanner og aktivere regex strengen til et brugbart regular ekspression mønster
        Pattern pattern = Pattern.compile(regex);
        /* try(

                Scanner scanner = new Scanner(new FileInputStream(new File((filePath))))

        ){*/
         try{
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.ISO_8859_1));

             //Brug bufferedReader objektet,
             //gem filens indhold i et Stream objekter med datetypen String (hver læst linje fra filen)
             //og spring den første linje over ved start
             Stream<String> studentsFile = bufferedReader.lines().skip(1);

             //Gem indhold fra strengen i en List
             List<String> students = studentsFile.collect(Collectors.toList());

             //Instansiere en Arrayliste til at opbevare Student objekter
             ArrayList<Student> studentArrayList = new ArrayList<>();

             //Benyt/kald forEach metoden på students objektet
             //og anvend det compilede regex mønster
             //på hver fundne streng for at fange de navngivet 'capture groups'
             //og indsæt oplysningerne derfra ind i et nyt Student objekt med argumenter
             //på de parametre der svare til deres reference
             students.forEach( s -> {

                 //midlertidige variabler
                 String username ="";
                 String firstname="";
                 String lastname="";
                 String classorigin ="";

                 Matcher matcher = pattern.matcher(s);

                 //While der kører så længe der findes et match med regex strengen
                 while(matcher.find()) {
                     username = matcher.group("USERNAME");
                     firstname = matcher.group("FIRSTNAME");
                     lastname = matcher.group("LASTNAME");
                     classorigin = matcher.group("CLASSORIGIN");
                 }

                 //Undtagelse for Rasmus, giver ham en A class origin klassificering
                 //Sidenote: kunne lige så godt have benyttet en struktur med Random klassen
                 //til at gøre det tilfældig per gang
                 classorigin = classorigin.equals("") ? "A" : classorigin;

                 //Student array listen samler det nylige konstrueret Student objekt op
                 studentArrayList.add(new Student(username ,firstname, lastname, classorigin));

             });


             System.out.println("Data indholdet af filen:");
             studentArrayList.forEach(System.out::println);

             //Gruppere efter class origin i et go
             groupByClassOriginInOneGo(studentArrayList);

             //Alternativ med at filtre enkeltvis
             /*groupByA(studentArrayList);

             groupByB(studentArrayList);

             groupByC(studentArrayList);

             groupByD(studentArrayList);
             */

             sortByLastname(studentArrayList);

             countClassOrigin(studentArrayList);

             generateStudyGroups(studentArrayList);

             bufferedReader.close();

             /*
             //System.out.println(scanner);

            while (scanner.hasNext()){

                System.out.println(""+scanner.nextLine().getBytes("ISO-8859-1"));

            }

            scanner.close();

             //Old fashioned måde at løbe en stream af strenge igennem
             //String readString = null;
             //while ((readString = bufferedReader.readLine()) != null){

             //System.out.println(readString);

             //}

             //Måden hvorpå man benytter en funktionel Stream collection af strenge
             //Til at løbe/læse/parse filen igennem


              */

        }catch(Exception e){

            System.out.println(e.getMessage());

        }
    }

    //Opgave 1.a : gruppere efter class origin (CLASSORIGIN)

    //Gruppere ved class origin A
    public void groupByA(ArrayList<Student> group){

        Stream<Student> groupA = group.stream().filter( s->s.getClassOrigin().equals("A"));

        //En måde at håndtere lambda udtryk på
        System.out.println("Group A");
        groupA.forEach( s -> {
            System.out.println(s.toString());
        });
    }

    //Gruppere ved class origin B
    public void groupByB(ArrayList<Student> group){

        Stream<Student> groupB = group.stream().filter( s->s.getClassOrigin().equals("B"));

        //Den forkortede/sammentrukken udgave af hvordan man håndtere lambda udtryk
        System.out.println("Group B");
        groupB.forEach(System.out::println);
    }

    //Gruppere ved class origin C
    public void groupByC(ArrayList<Student> group){

        Stream<Student> groupC = group.stream().filter( s->s.getClassOrigin().equals("C"));

        //Note: se kommentar under 'groupByB' metoden
        System.out.println("Group C");
        groupC.forEach(System.out::println);
    }

    //Gruppere ved class origin D
    public void groupByD(ArrayList<Student> group){

        Stream<Student> groupD = group.stream().filter( s->s.getClassOrigin().equals("D"));

        //-||-
        System.out.println("Group D");
        groupD.forEach(System.out::println);
    }

    public void groupByClassOriginInOneGo(ArrayList<Student> group){

        //ArrayList<Student> sortByClassOrigin = group;

        group.sort(Comparator.comparing(Student::getClassOrigin));

        System.out.println("Class origin sat i rækkefølge i et go");
        group.forEach(System.out::println);

    }

    //Opgave 1.b : sortere efter efternavn

    //Gruppere ved efternavn
    public void sortByLastname(ArrayList<Student> group){

        ArrayList<Student> sortedByLastname = group;

        //En måde at gøre det
        sortedByLastname.sort((Student s1, Student s2) -> (int) s1.getLastName().compareTo(s2.getLastName()) );
        sortedByLastname.forEach(System.out::println);

        //En anden måde at gøre det (int) er virkeligheden redundant
        sortedByLastname.sort((Student s1, Student s2) -> s1.getLastName().compareTo(s2.getLastName()) );

        //En endnu kortere måde at gøre det på... de to ovenover har i virkligheden overflødig tekst
        //Kan forkortes helt ned til Comparator.comparing(Object::getMetode) :
        //altså: Comparator.comparing(Student::getLastName) i dette tilfælde
        sortedByLastname.sort(Comparator.comparing(Student::getLastName));

        //Kun med 'efternavn' strenge
        Stream<String> groupLastname = group.stream().map(Student::getLastName).sorted(String::compareTo);
        groupLastname.forEach(System.out::println);

    }

    //Opagave 2.a : tæl antal per class origin
    public void countClassOrigin(ArrayList<Student> group){

        int countA = (int) group.stream().filter(s -> s.getClassOrigin().equals("A")).count();
        int countB = (int) group.stream().filter(s -> s.getClassOrigin().equals("B")).count();
        int countC = (int) group.stream().filter(s -> s.getClassOrigin().equals("C")).count();
        int countD = (int) group.stream().filter(s -> s.getClassOrigin().equals("D")).count();

        System.out.println("Class origin A: "+ countA +"\nClass origin B: "+ countB +"\nClass origin C: "+ countC +"\nClass origin D: "+ countD);

    }

    //Opgave 2.b : opret en studie gruppe bestående af medlemmer fra et hvert class origin
    public void generateStudyGroups(ArrayList<Student> group){

        List<Student> innerGroup = group;

        /**Personal notes, se bort fra kommentarne nedenunder:
        *
        *Start med faste udvalgte (de første fra hvert origin der bliver 'stødt ind på' (fundet) bliver optaget)
        *Prøv at randomize hvilket medlem bliver optaget efterfølgende
        *
        *Steps:
        *1.  opret en lokal variable til at opbevare en gruppering af fire studerende
        *2.  gennem løb ArrayListen og find et medlem fra hvert class origin,
        *2.b stil betingelser for at den kun finder/tager en af hvert class origin (distinct()?)
        *3.c og luk så snart når et class origin er opfanget
        *3.  tilføj de individuel medlemmer til variablen
        *4.  print indholdet af variablen ud
        *
        */

       //Relativt 'hardcoded' one-liner løsning på at oprette en gruppe bestående af 4 unikke class origins (A,B,C,D),
        //MEN opfanger KUN det første elemet fra hver gruppering
       //innerGroup.stream().collect(Collectors.groupingBy(Student::getClassOrigin)).values().forEach( s -> System.out.println( s.get(0).toString()));

       //TODO: randomize hvilke studerende der blive valgt per omgang:
       // således at resultat er vilkårligt hver gang lambda kæden er kaldt
       // Formindtligt er (... s.get(N) ...) der skal ændre hvor 'N' er en
       // randomized gyldig værdi som befinder sig indenfor intervallet for hvert samlet antal af class origin

        Random random = new Random();
        int randomInt = random.nextInt(10);

        innerGroup.stream().collect(Collectors.groupingBy(Student::getClassOrigin)).values().forEach( s -> {
            System.out.println( s.get(randomInt).toString() != null ? s.get(randomInt).toString() : null);
        });
    }

}
