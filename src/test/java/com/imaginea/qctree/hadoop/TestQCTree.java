package com.imaginea.qctree.hadoop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.imaginea.qctree.Cell;
import com.imaginea.qctree.Class;

public class TestQCTree {

  private QCTree tree;

  @Before
  public void setUp() throws IOException {
    Class root = new Class();
    root.setUpperBound(new Cell(new String[] { "*", "*", "*" }));
    root.setLowerBound(new Cell(new String[] { "*", "*", "*" }));
    root.setChildID(-1);
    root.setClassID(0);
    root.setAggregate(9);
    tree = new QCTree(root);

    Class clazz = new Class();
    clazz.setUpperBound(new Cell(new String[] { "*", "P1", "*" }));
    clazz.setLowerBound(new Cell(new String[] { "*", "P1", "*" }));
    clazz.setChildID(5);
    clazz.setClassID(6);
    clazz.setAggregate(7.5);
    tree.add(clazz);

    clazz = new Class();
    clazz.setUpperBound(new Cell(new String[] { "S1", "P1", "s" }));
    clazz.setLowerBound(new Cell(new String[] { "*", "P1", "s" }));
    clazz.setChildID(5);
    clazz.setClassID(6);
    clazz.setAggregate(6);
    tree.add(clazz);

    clazz = new Class();
    clazz.setUpperBound(new Cell(new String[] { "S1", "P2", "s" }));
    clazz.setLowerBound(new Cell(new String[] { "*", "P2", "*" }));
    clazz.setChildID(5);
    clazz.setClassID(6);
    clazz.setAggregate(12);
    tree.add(clazz);

    clazz = new Class();
    clazz.setUpperBound(new Cell(new String[] { "S1", "P1", "t" }));
    clazz.setLowerBound(new Cell(new String[] { "*", "P1", "*" }));
    clazz.setChildID(5);
    clazz.setClassID(6);
    clazz.setAggregate(12);
    tree.add(clazz);
  }

  @Test
  public void testShouldConstructQCTree() throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("ALL = * : 9.0 true").append('\n');
    sb.append("dim2 = P1 : 7.5 false").append('\n');
    sb.append("dim1 = S1 : 6.0 true").append('\n');
    sb.append("dim2 = P1 : 6.0 true").append('\n');
    sb.append("dim3 = s : 6.0 false").append('\n');
    sb.append("dim3 = t : 12.0 false").append('\n');
    sb.append("NONE = NONE : 0.0").append('\n');
    sb.append("dim2 = P2 : 12.0 true").append('\n');
    sb.append("dim3 = s : 12.0 false").append('\n');
    sb.append("NONE = NONE : 0.0").append('\n');
    sb.append("NONE = NONE : 0.0").append('\n');
    sb.append("NONE = NONE : 0.0").append('\n');

    Assert.assertEquals(tree.toString(), sb.toString());
  }

//  @Test
//  public void testShouldPerformSerDeCorectly() throws IOException {
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    DataOutput out = new DataOutputStream(baos);
//    tree.write(out);
//
//    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//    DataInput in = new DataInputStream(bais);
//    QCTree tree1 = new QCTree();
//    tree1.readFields(in);
//
//    Assert.assertEquals(tree, tree1);
//  }

}
