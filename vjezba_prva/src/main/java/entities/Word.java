package entities;

import org.jgap.*;

/**
 * Created by marko on 10/31/15.
 */
public class Word {

    private String content;
    private int length;
    private long value;

    public Word(String content) {
        this.content = content;
        this.length = content.length();
        value = calculateWordValue(content);
    }

    private long calculateWordValue(String content) {
        long value = 0;
        for (char ch : content.toCharArray()) {
            value += (long) ch;
        }

        Gene gene = new Gene() {
            @Override
            public Gene newGene() {
                return null;
            }

            @Override
            public void setAllele(Object a_newValue) {

            }

            @Override
            public Object getAllele() {
                return null;
            }

            @Override
            public String getPersistentRepresentation() throws UnsupportedOperationException {
                return null;
            }

            @Override
            public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException, UnsupportedRepresentationException {

            }

            @Override
            public void setToRandomValue(RandomGenerator a_numberGenerator) {

            }

            @Override
            public void cleanup() {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public void applyMutation(int index, double a_percentage) {

            }

            @Override
            public void setApplicationData(Object a_newData) {

            }

            @Override
            public Object getApplicationData() {
                return null;
            }

            @Override
            public void setCompareApplicationData(boolean a_doCompare) {

            }

            @Override
            public boolean isCompareApplicationData() {
                return false;
            }

            @Override
            public double getEnergy() {
                return 0;
            }

            @Override
            public void setEnergy(double a_energy) {

            }

            @Override
            public void setConstraintChecker(IGeneConstraintChecker a_constraintChecker) {

            }

            @Override
            public Configuration getConfiguration() {
                return null;
            }

            @Override
            public int compareTo(Object o) {
                return 0;
            }
        };
        return value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
