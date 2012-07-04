package com.imaginea.qctree.measures;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.imaginea.qctree.Row;

public class Maximum implements Aggregable {

  private static final Log LOG = LogFactory.getLog(Maximum.class);

  private double max = Double.MIN_VALUE;

  @Override
  public void readFields(DataInput in) throws IOException {
    this.max = in.readDouble();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeDouble(max);
  }

  @Override
  public void aggregate(List<Row> rows) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Computing Maximum Aggregate");
    }
    for (Row row : rows) {
      for (double value : row.getMeasures()) {
        max = Math.max(max, value);
      }
    }
  }

  @Override
  public double getAggregateValue() {
    return max;
  }

  @Override
  public void accumalate(Aggregable other) {
    Maximum otherMax = (Maximum) other;
    this.max = Math.max(this.max, otherMax.max);
  }

  @Override
  public String toString() {
    return String.valueOf(max);
  }

}
