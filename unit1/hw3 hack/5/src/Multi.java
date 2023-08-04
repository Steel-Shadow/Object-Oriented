import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Multi {
    public Monomial monomult(Monomial monomial1, Monomial monomial2) {
        Monomial result = new Monomial();
        result.setCoefficient(monomial1.getCoefficient().multiply(monomial2.getCoefficient()));
        for (String string : monomial1.getVariables()) {
            if (!result.getVariables().contains(string)) {
                result.getVariables().add(string);
                result.getIndexTable().put(string, monomial1.getIndexTable().get(string));
            } else {
                result.getIndexTable().put(string,
                        result.getIndexTable().get(string) + monomial1.getIndexTable().get(string));
            }
        }
        for (String string : monomial2.getVariables()) {
            if (!result.getVariables().contains(string)) {
                result.getVariables().add(string);
                result.getIndexTable().put(string, monomial2.getIndexTable().get(string));
            } else {
                result.getIndexTable().put(string,
                        result.getIndexTable().get(string) + monomial2.getIndexTable().get(string));
            }
        }
        for (TriFac triFac : monomial1.getTriFacs()) {
            if (!result.getTriFacs().isEmpty()) {
                result.getTriFacs().add(triFac);
            } else {
                int flag = 0;
                for (TriFac triFac1 : result.getTriFacs()) {
                    if (triFac1.getExpPoly().equals(triFac.getExpPoly())) {
                        triFac1.setIndex(triFac.getIndex() + triFac1.getIndex());
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    result.getTriFacs().add(triFac);
                }
            }
        }
        for (TriFac triFac : monomial2.getTriFacs()) {
            if (!result.getTriFacs().isEmpty()) {
                result.getTriFacs().add(triFac);
            } else {
                int flag = 0;
                for (TriFac triFac1 : result.getTriFacs()) {
                    if (triFac1.getExpPoly().equals(triFac.getExpPoly())) {
                        triFac1.setIndex(triFac.getIndex() + triFac1.getIndex());
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    result.getTriFacs().add(triFac);
                }
            }
        }
        return result;
    }

    public Polynomial polymult(Polynomial poly1, Polynomial poly2) {
        Multi multi = new Multi();
        Monomial base = new Monomial(new BigInteger("1"), new ArrayList<>(),
                new HashMap<>(), new ArrayList<>());
        Polynomial result = new Polynomial();
        result.getMonomials().add(base);
        Polynomial temp = new Polynomial();
        for (Monomial mono1 : result.getMonomials()) {
            for (Monomial momo2 : poly1.getMonomials()) {
                Monomial calResult = multi.monomult(mono1, momo2);
                int flag = 0;
                for (Monomial mono3 : temp.getMonomials()) {
                    if (mono3.like(calResult)) {
                        mono3.setCoefficient(mono3.getCoefficient()
                                .add(calResult.getCoefficient()));
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    temp.getMonomials().add(calResult);
                }
            }
        }
        result.setMonomials(new ArrayList<>());
        result.getMonomials().addAll(temp.getMonomials());
        Polynomial temp1 = new Polynomial();
        for (Monomial mono1 : result.getMonomials()) {
            for (Monomial momo2 : poly2.getMonomials()) {
                Monomial calResult = multi.monomult(mono1, momo2);
                int flag = 0;
                for (Monomial mono3 : temp1.getMonomials()) {
                    if (mono3.like(calResult)) {
                        mono3.setCoefficient(mono3.getCoefficient()
                                .add(calResult.getCoefficient()));
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    temp1.getMonomials().add(calResult);
                }
            }
        }
        result.setMonomials(new ArrayList<>());
        result.getMonomials().addAll(temp1.getMonomials());
        return result;
    }
}
