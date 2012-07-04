package com.imaginea.qctree.measures;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.imaginea.qctree.Row;

public class Count implements Aggregable {

  private static final Log LOG = LogFactory.getLog(Count.class);
  private int count;

  @Override
  public void readFields(DataInput in) throws IOException {
    count = in.readInt();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(count);
  }

  @Override
  public void aggregate(List<Row> rows) {
    if (LOG.isDebugEnabled()) {
      LOG.info("Computing Count Aggregate");
    }
    this.count = rows.size();
  }

  @Override
  public double getAggregateValue() {
    return count;
  }

  @Override
  public void accumalate(Aggregable other) {
    Count otherCount = (Count) other;
    this.count += otherCount.count;
  }

  @Override
  public String toString() {
    return String.valueOf(count);
  }

}
