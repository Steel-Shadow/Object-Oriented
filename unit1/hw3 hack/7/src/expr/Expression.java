package expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class Expression implements Cloneable {
    private ArrayList<Variable> variables;

    private boolean isSimple;

    public Expression() {
        this.isSimple = false;
        this.variables = new ArrayList<>();
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public void addVariables(ArrayList<Variable> variables) {
        this.variables.addAll(variables);
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public int getCount() {
        return variables.size();
    }

    public boolean isZero() {
        boolean k = true;
        for (Variable v: variables) {
            k &= v.isZero();
        }
        return k;
    }

    public boolean comp(Expression src) {
        simplify();
        src.simplify();
        if (getCount() != src.getCount()) {
            return false;
        }
        ArrayList<Variable> variables1 = src.getVariables();
        HashMap<Variable, Boolean> vis = new HashMap<>();
        for (Variable variable : variables) {
            int flag = 0;
            for (Variable variable1 : variables1) {
                if (vis.containsKey(variable1)) {
                    continue;
                }
                if (variable.equals(variable1)) {
                    flag = 1;
                    vis.put(variable1, true);
                    break;
                }
            }
            if (flag == 0) {
                return false;
            }
        }
        return true;
    }

    public void simplify() {
        if (isSimple) {
            return;
        }
        Collections.sort(variables, new Comparator<Variable>() {
            @Override
            public int compare(Variable o1, Variable o2) {
                if (o1.getXidx() == o2.getXidx()) {
                    if (o1.getYidx() == o2.getYidx()) {
                        if (o1.getZidx() == o2.getZidx()) {
                            return o1.getCount() - o2.getCount();
                        }
                        return o1.getZidx() - o2.getZidx();
                    }
                    return o1.getYidx() - o2.getYidx();
                }
                return o1.getXidx() - o2.getXidx();
            }
        });
        Iterator<Variable> iter = variables.iterator();
        Variable now = iter.next();
        while (iter.hasNext()) {
            Variable nxt = iter.next();
            if (now.comp(nxt)) {
                now.merge(nxt);
                iter.remove();
            }
            else {
                now = nxt;
            }
        }
        isSimple = true;
    }

    public Expression derive(String var) throws CloneNotSupportedException {
        simplify();
        Expression e = new Expression();
        for (Variable v: variables) {
            e.addVariables(v.derive(var));
        }
        return e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Variable variable : variables) {
            sb.append(variable);
        }
        if (sb.toString().equals("")) {
            return "0";
        }
        return sb.toString();
    }

    @Override
    public Expression clone() throws CloneNotSupportedException {
        Expression clone = (Expression) super.clone();
        clone.variables = new ArrayList<>();
        for (Variable variable : variables) {
            clone.variables.add(variable.clone());
        }
        return clone;
    }
}
