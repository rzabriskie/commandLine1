package rsz.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CREATE TABLE "names"
 (
 firstname "char",
 lastname "char"
 )
 WITH (
 OIDS = FALSE
 )

 TABLESPACE pg_default;
 ALTER TABLE "names" OWNER TO postgres;
 GRANT ALL ON TABLE "names" TO public;

 @author Rzabriskie

 */
public class Main {

    public static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static final long NUMBER_OF_ROWS = 1234; //1234567;

    public static void main(String[] args) {
        LOGGER.trace("test...");
        LOGGER.error("test...");

        //Persistence.test();

        BatchPersistable persister = new BatchPostgresPersister();

        persister.truncate("names");

        long start = System.nanoTime();
        for (long i = 0; i< NUMBER_OF_ROWS; i++) {
//            persister.addToBatch(String.format("insert into names values('%d','%d')",i,i));
            persister.addToBatch(String.valueOf(i),String.valueOf(i));
            if (i % 10000 == 0) {
                LOGGER.info(String.format("row count %d",i));
            }
        }

        LOGGER.info("Commiting batch...");
        persister.commitBatch();
        long end = System.nanoTime();

        LOGGER.info(String.format("Total elapsed time is %d seconds to insert %d rows",
                ((end - start) / 1000000000),
                NUMBER_OF_ROWS));

    }
}
