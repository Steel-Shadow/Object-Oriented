import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Monomial {
    private BigInteger coefficient = new BigInteger("0");
    private ArrayList<String> variables = new ArrayList<>();
    private HashMap<String, Integer> indexTable = new HashMap<>();
    private ArrayList<TriFac> triFacs = new ArrayList<>();

    public BigInteger getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigInteger coefficient) {
        this.coefficient = coefficient;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public HashMap<String, Integer> getIndexTable() {
        return indexTable;
    }

    public void setIndexTable(HashMap<String, Integer> indexTable) {
        this.indexTable = indexTable;
    }

    public ArrayList<TriFac> getTriFacs() {
        return triFacs;
    }

    public void setTriFacs(ArrayList<TriFac> triFacs) {
        this.triFacs = triFacs;
    }

    public Monomial(BigInteger coefficient, ArrayList<String> variables,
                    HashMap<String, Integer> indexTable, ArrayList<TriFac> triFacs) {
        this.coefficient = coefficient;
        this.variables = variables;
        this.indexTable = indexTable;
        this.triFacs = triFacs;
    }

    public Monomial() {

    }

    public boolean equals(Monomial mono) {
        if (!this.coefficient.equals(mono.getCoefficient())) {
            return false;
        }
        if (this.variables.size() != mono.variables.size()) {
            return false;
        }
        if (!this.getVariables().isEmpty() && !mono.getVariables().isEmpty()) {
            int signal = 1;
            for (String string1 : this.getVariables()) {
                if (!mono.getVariables().contains(string1)
                        || this.getIndexTable().get(string1) !=
                        mono.getIndexTable().get(string1)) {
                    signal = 0;
                    break;
                }
            }
            if (signal == 0) {
                return false;
            }
        }
        if (this.triFacs.size() != mono.triFacs.size()) {
            return false;
        }
        if (!this.triFacs.isEmpty() && !mono.triFacs.isEmpty()) {
            int cnt1 = 0;
            int cnt2 = 0;
            int cnt3 = 0;
            int cnt4 = 0;
            for (TriFac triFac : this.triFacs) {
                if (triFac.getType() == 1) {
                    cnt1++;
                } else {
                    cnt2++;
                }
            }
            for (TriFac triFac : mono.triFacs) {
                if (triFac.getType() == 1) {
                    cnt3++;
                } else {
                    cnt4++;
                }
            }
            if (cnt1 != cnt3 || cnt2 != cnt4) {
                return false;
            }
            return judge(this.triFacs, mono.triFacs);
        }
        return true;
    }

    public boolean like(Monomial mono) {
        if (this.variables.size() != mono.variables.size()) {
            return false;
        }
        if (!this.getVariables().isEmpty() && !mono.getVariables().isEmpty()) {
            int signal = 1;
            for (String string1 : this.getVariables()) {
                if (!mono.getVariables().contains(string1)
                        || this.getIndexTable().get(string1) !=
                        mono.getIndexTable().get(string1)) {
                    signal = 0;
                    break;
                }
            }
            if (signal == 0) {
                return false;
            }
        }
        if (this.triFacs.size() != mono.triFacs.size()) {
            return false;
        }
        if (!this.triFacs.isEmpty() && !mono.triFacs.isEmpty()) {
            int cnt1 = 0;
            int cnt2 = 0;
            int cnt3 = 0;
            int cnt4 = 0;
            for (TriFac triFac : this.triFacs) {
                if (triFac.getType() == 1) {
                    cnt1++;
                } else {
                    cnt2++;
                }
            }
            for (TriFac triFac : mono.triFacs) {
                if (triFac.getType() == 1) {
                    cnt3++;
                } else {
                    cnt4++;
                }
            }
            if (cnt1 != cnt3 || cnt2 != cnt4) {
                return false;
            }
            return judge(this.triFacs, mono.triFacs);
        }
        return true;
    }

    public boolean judge(ArrayList<TriFac> triFacs1, ArrayList<TriFac> triFacs2) {
        ArrayList<TriFac> temp1 = new ArrayList<>();
        ArrayList<TriFac> temp2 = new ArrayList<>();
        temp1.addAll(triFacs1);
        temp2.addAll(triFacs2);
        for (TriFac triFac1 : triFacs1) {
            for (TriFac triFac2 : triFacs2) {
                if (triFac1.getIndex() == triFac2.getIndex() &&
                        triFac1.getExpPoly().equals(triFac2.getExpPoly())) {
                    temp1.remove(triFac1);
                    temp2.remove(triFac2);
                    break;
                }
            }
        }
        return temp1.isEmpty() && temp2.isEmpty();
    }

    public String toString() {
        this.checkEmptyVars();
        StringBuilder sb = new StringBuilder();
        if (this.coefficient.equals(new BigInteger("0"))) {
            sb.append("0");
        } else {
            if (this.getVariables().isEmpty() && this.triFacs.isEmpty()) {
                sb.append(this.getCoefficient());
            } else {
                if (this.getCoefficient().equals(new BigInteger("1"))) {
                    sb.append("");
                } else if (this.getCoefficient().equals(new BigInteger("-1"))) {
                    sb.append("-");
                } else {
                    sb.append(this.getCoefficient());
                    sb.append("*");
                }
                if (!this.getVariables().isEmpty() && this.triFacs.isEmpty()) {
                    sb.append(printVars());
                } else if (this.getVariables().isEmpty() && !this.triFacs.isEmpty()) {
                    sb.append(printTris());
                } else {
                    sb.append(printVars());
                    sb.append("*");
                    sb.append(printTris());
                }
            }
        }
        return sb.toString();
    }

    public void checkEmptyVars() {
        if (this.getVariables().isEmpty()) {
            this.getIndexTable().clear();
        } else {
            int flag = 0;
            for (String string : this.getVariables()) {
                if (!string.equals("")) {
                    if (this.getIndexTable().get(string) > 0) {
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                this.getVariables().clear();
                this.getIndexTable().clear();
            }
        }
    }

    public String printVars() {
        StringBuilder sb = new StringBuilder();
        for (String string : this.getVariables()) {
            if (this.getIndexTable().get(string) == 0) {
                sb.append("");
            } else {
                if (this.getIndexTable().get(string) != 1) {
                    sb.append(string);
                    sb.append("**");
                    sb.append(this.getIndexTable().get(string));
                } else {
                    sb.append(string);
                }
            }
            sb.append("*");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String printTris() {
        StringBuilder sb = new StringBuilder();
        for (TriFac triFac : this.triFacs) {
            if (triFac.getIndex() == 0) {
                sb.append("");
            } else {
                if (triFac.getType() == 1) {
                    sb.append("sin((");
                } else {
                    sb.append("cos((");
                }
                sb.append(triFac.getExpPoly().toString());
                sb.append("))");
                if (triFac.getIndex() != 1) {
                    sb.append("**");
                    sb.append(triFac.getIndex());
                }
                sb.append("*");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Polynomial diff(String tar) {
        Polynomial result = new Polynomial();
        int flag1 = 0;
        this.checkEmptyVars();
        if (!this.variables.isEmpty()) {
            if (this.variables.contains(tar)) {
                result.getMonomials().add(diffPowerPart(tar));
                flag1 = 1;
            }
        }
        int flag2 = 0;
        if (!this.triFacs.isEmpty()) {
            for (TriFac triFac : this.triFacs) {
                if (triFac.getExpPoly().haveVar(tar)) {
                    flag2 = 1;
                    break;
                }
            }
        }
        if (flag2 == 1) {
            result.getMonomials().addAll(diffTriPart(tar).getMonomials());
        }
        if (flag1 == 0 && flag2 == 0) {
            result.getMonomials().add(new Monomial(new BigInteger("0"),
                    new ArrayList<>(), new HashMap<>(), new ArrayList<>()));
        }
        return result;
    }

    public Monomial diffPowerPart(String tar) {
        Monomial temp = new Monomial();
        ArrayList<String> tempArray = new ArrayList<>();
        HashMap<String, Integer> tempHash = new HashMap<>();
        ArrayList<TriFac> tempTriFac = new ArrayList<>();
        tempTriFac.addAll(this.triFacs);
        if (this.indexTable.get(tar) == 1) {
            temp.coefficient = new BigInteger("0").add(this.coefficient);
            for (String string : this.variables) {
                if (!string.equals(tar)) {
                    tempArray.add(string);
                }
            }
            for (String string : this.variables) {
                if (!string.equals(tar)) {
                    tempHash.put(string, this.indexTable.get(string));
                }
            }
        } else {
            temp.coefficient = this.coefficient.multiply(
                    new BigInteger(String.valueOf(this.indexTable.get(tar))));
            for (String string : this.variables) {
                tempArray.add(string);
            }
            for (String string : this.variables) {
                if (!string.equals(tar)) {
                    tempHash.put(string, this.indexTable.get(string));
                } else {
                    tempHash.put(string, this.indexTable.get(string) - 1);
                }
            }
        }
        temp.variables = tempArray;
        temp.indexTable = tempHash;
        temp.triFacs = tempTriFac;
        return temp;
    }

    public Polynomial diffTriPart(String tar) {
        Multi multi = new Multi();
        Polynomial finalResult = new Polynomial();
        for (TriFac triFac : this.triFacs) {
            if (triFac.getExpPoly().haveVar(tar)) {
                Monomial mono = new Monomial();
                mono.setCoefficient(new BigInteger("0").add(this.getCoefficient()));
                mono.variables.addAll(this.getVariables());
                mono.indexTable.putAll(this.getIndexTable());
                int flag = 0;
                for (TriFac triFac1 : this.triFacs) {
                    if (!triFac1.equals(triFac)) {
                        mono.triFacs.add(triFac1);
                    } else if (triFac1.equals(triFac) && flag == 0) {
                        flag = 1;
                        if (triFac1.getType() == 1) {
                            TriFac triFac2 = new TriFac(2, 1,
                                    triFac1.getExpPoly());
                            mono.triFacs.add(triFac2);
                        } else {
                            mono.setCoefficient(mono.getCoefficient().negate());
                            TriFac triFac2 = new TriFac(1, 1,
                                    triFac1.getExpPoly());
                            mono.triFacs.add(triFac2);
                        }
                        if (triFac1.getIndex() > 1) {
                            mono.coefficient = mono.coefficient.multiply(
                                    new BigInteger(String.valueOf(triFac.getIndex())));
                            TriFac triFac3 = new TriFac(triFac1.getType(), triFac1.getIndex() - 1,
                                    triFac1.getExpPoly());
                            mono.triFacs.add(triFac3);
                        }
                    }
                    else {
                        mono.triFacs.add(triFac1);
                    }
                }
                Polynomial result = new Polynomial();
                result.getMonomials().add(mono);
                Polynomial partResult = multi.polymult(result, triFac.getExpPoly().diff(tar));
                finalResult.getMonomials().addAll(partResult.getMonomials());
            }
        }
        return finalResult;
    }

    public boolean haveVar(String tar) {
        for (String string : this.variables) {
            if (string.equals(tar)) {
                return true;
            }
        }
        for (TriFac triFac : this.triFacs) {
            if (triFac.getExpPoly().haveVar(tar)) {
                return true;
            }
        }
        return false;
    }
}
