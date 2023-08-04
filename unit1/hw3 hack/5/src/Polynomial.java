import java.math.BigInteger;
import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Monomial> monomials = new ArrayList<>();

    public ArrayList<Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
    }

    public boolean equals(Polynomial poly) {
        int flag = 1;
        if (this.monomials.size() != poly.getMonomials().size()) {
            flag = 0;
        } else {
            ArrayList<Monomial> monos1 = new ArrayList<>();
            ArrayList<Monomial> monos2 = new ArrayList<>();
            monos1.addAll(this.monomials);
            monos2.addAll(poly.monomials);
            for (Monomial mono1 : this.monomials) {
                for (Monomial mono2 : poly.getMonomials()) {
                    if (mono1.equals(mono2)) {
                        monos1.remove(mono1);
                        monos2.remove(mono2);
                        break;
                    }
                }
            }
            if (!monos1.isEmpty() || !monos2.isEmpty()) {
                flag = 0;
            }
        }
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Monomial monomial : this.monomials) {
            if (!monomial.getCoefficient().equals(new BigInteger("0"))) {
                sb.append(monomial.toString());
                sb.append("+");
            }
        }
        if (sb.length() == 0) {
            sb.append(0);
        } else {
            sb.deleteCharAt(sb.length() - 1);
        }
        String answer = sb.toString();
        answer = answer.replace("++", "+");
        answer = answer.replace("+-", "-");
        answer = answer.replace("--", "+");
        answer = answer.replace("-+", "-");
        return answer;
    }

    public boolean haveVar(String tar) {
        for (Monomial mono : this.monomials) {
            if (mono.haveVar(tar)) {
                return true;
            }
        }
        return false;
    }

    public Polynomial diff(String tar) {
        Polynomial result = new Polynomial();
        for (Monomial mono : this.getMonomials()) {
            result.getMonomials().addAll(mono.diff(tar).getMonomials());
        }
        return result;
    }
}
