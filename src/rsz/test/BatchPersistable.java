package rsz.test;

/**
 * Created by rzabrisk on 2/1/17.
 */
public interface BatchPersistable {

    public void addToBatch(final String sqlInsert);

    public void commitBatch();

    public void truncate(String tablename);

}
