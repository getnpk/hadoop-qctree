package com.imaginea.qctree;

import java.util.List;
import java.util.Map.Entry;

import com.imaginea.qctree.measures.Aggregable;
import com.imaginea.qctree.measures.Average;

public class Partition {

  private String ID;
  private Cell ub;
  private Cell lb;
  private List<Row> baseCells;
  private Aggregable measure = new Average();
  private Double aggregateVal;

  private Table baseTable;

  public Partition(String ID, List<Row> cells) {
    this.ID = ID;
    this.baseCells = cells;
  }

  public Partition inducedBy(Cell cell) {
    return null;
  }

  public double computeAggregateAndGet() {
    if (aggregateVal != null) {
      return aggregateVal;
    }
    aggregateVal = measure.aggregate(baseCells, ub);
    return aggregateVal;
  }

  private String getDimensionValueAt(int colIndex) {
    boolean hasAppeared = true;
    String commonVal = null;
    for (Cell cell : baseCells) {
      if (cell.compareTo(ub) != 0) {
        continue;
      }
      if (commonVal != null
          && !commonVal.equals(cell.getDimensions()[colIndex])) {
        hasAppeared = false;
        break;
      } else {
        commonVal = cell.getDimensions()[colIndex];
      }
      if (!hasAppeared) {
        break;
      }
    }
    return hasAppeared ? commonVal : Cell.DIMENSION_VALUE_ANY;
  }

  /**
   * Upper bound of a partition will be computed as follows.
   * 
   * 1. For any dimension, if the value of that dimension is non-*, upper bound
   * also will have the same dimension value as that of the input cell.
   * 2.Otherwise, for all the dimensions, for which input cell has value *, we
   * scan all the base cells in the partition, and if there is any value (x)
   * repeated in the partition, we replace the value of the ith dimension of
   * upper bound by repeated value (x).
   * 
   * Ex: For the partition {(a2,b1,c1),(a2,b2,c1),(a1,b1,c2)}, upper bound of
   * the cell (a2, *, *) is (a2, *, c1). Here c1 has appeared in all the tuples
   * but not b1.
   * 
   * @param cell
   *          input cell
   * @return Upper bound of the partition.
   * @throws CloneNotSupportedException
   */
  public Cell upperBoundOf(Cell cell) {
    if (ub != null) {
      return ub;
    }
    ub = (Cell) cell.clone();
    for(int colIndex = 0; colIndex < ub.getDimensions().length; ++colIndex) {
      if (ub.getDimensions()[colIndex] != Cell.DIMENSION_VALUE_ANY) {
        continue;
      }
      ub.setDimensionAt(colIndex, getDimensionValueAt(colIndex));
    }
    return ub;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ID").append("=").append(ID).append('\n');
    sb.append("Upper Bound").append("=").append(ub).append('\n');
    sb.append("Lower Bound").append("=").append(lb);
    return sb.toString();
  }
}