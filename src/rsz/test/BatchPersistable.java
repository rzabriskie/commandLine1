package rsz.test;

/**
 * Created by rzabrisk on 2/1/17.
 */
public interface BatchPersistable {

    public void addToBatch(final String sqlInsert);
    public void addToBatch(final String name, final String value);

    public void commitBatch();

    public void truncate(String tablename);

}
