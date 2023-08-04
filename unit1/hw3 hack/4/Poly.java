import java.math.BigInteger;
import java.util.ArrayList;

public class Poly {
    private final ArrayList<Unit> unitlist;

    public ArrayList<Unit> getUnitlist() {
        return unitlist;
    }

    public Poly(ArrayList<Unit> unitlist) {
        this.unitlist = unitlist;
    }

    public Boolean equalsPoly(Poly poly1, Poly poly2) {
        boolean equal = true;
        if (poly1.unitlist.size() == poly2.unitlist.size()) {
            for (int i = 0;i < poly1.unitlist.size();i++) {
                boolean find = false;
                for (int j = 0;j < poly2.unitlist.size();j++) {
                    if (poly1.unitlist.get(i).equalsUnit(poly1.unitlist
                            .get(i),poly2.unitlist.get(j))) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    equal = false;
                }
            }
        }
        else {
            equal = false;
        }
        return equal;
    }

    public Poly addPoly(Poly newPoly) {
        ArrayList<Unit> unitlist = new ArrayList<>();
        Poly basicPoly = new Poly(unitlist);
        for (int i = 0; i < newPoly.unitlist.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < basicPoly.unitlist.size(); j++) {
                if (newPoly.unitlist.get(i).sameUnit(newPoly.unitlist
                        .get(i), basicPoly.unitlist.get(j))) {
                    flag = true;
                    basicPoly.unitlist.get(j).setCoe(basicPoly.unitlist
                            .get(j).getCoe().add(newPoly.unitlist.get(i).getCoe()));
                    break;
                }
            }
            if (!flag) {
                basicPoly.unitlist.add(newPoly.unitlist.get(i));
            }
        }
        for (int i = 0; i < this.unitlist.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < basicPoly.unitlist.size(); j++) {
                if (this.unitlist.get(i).sameUnit(this.unitlist.get(i),
                        basicPoly.unitlist.get(j))) {
                    flag = true;
                    basicPoly.unitlist.get(j).setCoe(basicPoly.unitlist
                            .get(j).getCoe().add(this.unitlist.get(i).getCoe()));
                    break;
                }
            }
            if (!flag) {
                basicPoly.unitlist.add(this.unitlist.get(i));
            }
        }
        return basicPoly;
    }

    public Poly mulPoly(Poly newpoly) {
        ArrayList<Unit> unitlist = new ArrayList<>();
        Poly poly = new Poly(unitlist);
        for (int i = 0; i < this.unitlist.size(); i++) {
            for (int j = 0; j < newpoly.unitlist.size(); j++) {
                Unit unit = mulUnit(this.unitlist.get(i),newpoly.unitlist.get(j));
                poly.unitlist.add(unit);
            }
        }
        //通过调用addPoly合并同类项
        ArrayList<Unit> emunitlist = new ArrayList<>();
        Poly empoly = new Poly(emunitlist);
        return empoly.addPoly(poly);
    }

    public Poly powPoly(BigInteger a) {
        if (a.equals(BigInteger.ZERO)) {
            Unit unit = new Unit();
            unit.setCoe(BigInteger.ONE);
            ArrayList<Unit> emptyUnitlist = new ArrayList<>();
            emptyUnitlist.add(unit);
            return new Poly(emptyUnitlist);
        }
        else {
            Poly basicPoly = this;  //改变basicPoly会改变this么
            for (int i = 1; i < Integer.parseInt(a.toString()); i++) {
                basicPoly = basicPoly.mulPoly(this);
            }
            return basicPoly;
        }
    }

    public Poly negPloy(Poly poly) {
        for (int i = 0; i < poly.unitlist.size(); i++) {
            poly.unitlist.get(i).setCoe(poly.unitlist.get(i).getCoe().negate());
        }
        return poly;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Unit unit : unitlist) {
            sb.append(unit.toString());
        }
        return sb.toString();
    }

    public Unit mulUnit(Unit unit1, Unit unit2) {
        Unit unit = new Unit();
        unit.setCoe(unit1.getCoe().multiply(unit2.getCoe()));
        unit.setExpX(unit1.getExpX().add(unit2.getExpX()));
        unit.setExpY(unit1.getExpY().add(unit2.getExpY()));
        unit.setExpZ(unit1.getExpZ().add(unit2.getExpZ()));
        for (Poly poly1 : unit1.getSinMap().keySet()) {
            unit.getSinMap().put(poly1, unit1.getSinMap().get(poly1));
        }
        for (Poly poly1 : unit1.getCosMap().keySet()) {
            unit.getCosMap().put(poly1, unit1.getCosMap().get(poly1));
        }
        boolean flag;
        for (Poly poly2 : unit2.getCosMap().keySet()) {
            flag = false;
            for (Poly poly : unit.getCosMap().keySet()) {
                if (poly.equalsPoly(poly, poly2)) {
                    flag = true;
                    unit.getCosMap().replace(poly, unit.getCosMap().get(poly)
                                .add(unit2.getCosMap().get(poly2)));
                    break;
                }
            }
            if (!flag) {
                unit.getCosMap().put(poly2, unit2.getCosMap().get(poly2));
            }
        }
        for (Poly poly2 : unit2.getSinMap().keySet()) {
            flag = false;
            for (Poly poly : unit.getSinMap().keySet()) {
                if (poly.equalsPoly(poly, poly2)) {
                    flag = true;
                    unit.getSinMap().replace(poly, unit.getSinMap().get(poly)
                                .add(unit2.getSinMap().get(poly2)));
                    break;
                }
            }
            if (!flag) {
                unit.getSinMap().put(poly2, unit2.getSinMap().get(poly2));
            }
        }
        return unit;
    }   //这是一个纯纯的函数

    public Poly derive(int flag) {
        ArrayList<Unit> unitlist = new ArrayList<>();
        Poly poly = new Poly(unitlist);
        for (Unit unit : this.unitlist) {
            poly = poly.addPoly(unit.derive(flag));
        }
        return poly;
    }
}

