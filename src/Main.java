/**
 * Created by Vik on 4/23/2016.
 */
public class Main {
    public static void main(String args[]){
        System.out.println("LCS problem implementation");
        String str1 = "Cannabis remains the drug of choice in developed and developing countries, with up to 5 of 15-64 year olds around the world thought to use it, the researchers point out. While it never used to be a major cause for concern, recent research has pointed to links between the drug and an increased risk of road traffic accidents, psychosis, HIV, hepatitis, infective endocarditis and TB. Less is known, however, about its possible effects on fetal growth and development.factors known to be associated with a higher risk of low birthweight and premature birth.",
                str2 = "The researchers carried out a comprehensive trawl of seven research databases for studies published up to 2014, looking at the effects of cannabis use during pregnancy on mother and baby up to six weeks after the birth. Outcomes, such as anaemia in the mother, birthweight, baby's length, need for neonatal intensive care, head circumference and early birth were all included in the review of 24 studies. Analysis of the pooled data showed that mums- to-be who used cannabis were 36 more likely to have anaemia than women who didn't use the drug. Infantsrank";

//        LCS test1 = new LCS(str1, str2);\
        Test test = new Test(str1+str2+str1 + str2+str1+str2, str2+str1+str2 + str1+str2+str1);
        System.out.println("("+args[0]+")time: " + test.run(Integer.parseInt(args[0])));
//        System.out.println(test1.execute(1) + ": " + test1.solution_str());
//        System.out.println(test1.table_toString());
    }
}

class Test {
    long startTime, endTime;
    String str1, str2;

    public Test(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public long run(int threads) {
        LCS test = new LCS(str1, str2);
        startTime = System.currentTimeMillis();
        System.out.println("result " + test.execute(threads));
        endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.gc();
        System.runFinalization();
        return time;
    }
}