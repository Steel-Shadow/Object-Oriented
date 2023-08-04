import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    private BigInteger coe = BigInteger.ZERO;
    private BigInteger expX = BigInteger.ZERO;
    private BigInteger expY = BigInteger.ZERO;
    private BigInteger expZ = BigInteger.ZERO;
    private HashMap<Poly, BigInteger> sinMap = new HashMap<>();
    private HashMap<Poly, BigInteger> cosMap = new HashMap<>();

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public BigInteger getCoe() {
        return coe;
    }

    public void setExpX(BigInteger expX) {
        this.expX = expX;
    }

    public BigInteger getExpX() {
        return expX;
    }

    public void setExpY(BigInteger expY) {
        this.expY = expY;
    }

    public BigInteger getExpY() {
        return expY;
    }

    public void setExpZ(BigInteger expZ) {
        this.expZ = expZ;
    }

    public BigInteger getExpZ() {
        return expZ;
    }

    public HashMap<Poly, BigInteger> getSinMap() {
        return sinMap;
    }

    public HashMap<Poly, BigInteger> getCosMap() {
        return cosMap;
    }

    public Boolean equalsUnit(Unit unit1, Unit unit2) {
        boolean flag = true;
        if (!(unit1.coe.equals(unit2.coe) && unit1.expX.equals(unit2.expX)
                && unit1.expY.equals(unit2.expY) && unit1.expZ.equals(unit2.expZ)
                && unit1.sinMap.size() == unit2.sinMap.size()
                && unit1.cosMap.size() == unit2.cosMap.size())) {
            flag = false;
        } else {    //建立在sinMap已经合并同类项的基础上
            for (Poly poly2 : unit2.sinMap.keySet()) {
                boolean find = false;
                for (Poly poly1 : unit1.sinMap.keySet()) {
                    if (poly2.equalsPoly(poly1, poly2)) {
                        find = true;
                        flag = unit1.sinMap.get(poly1).equals(unit2.sinMap.get(poly2)) && flag;
                        break;
                    }
                }
                if (!find) {
                    flag = false;
                }
            }
            for (Poly poly2 : unit2.cosMap.keySet()) {
                boolean find = false;
                for (Poly poly1 : unit1.cosMap.keySet()) {
                    if (poly2.equalsPoly(poly1, poly2)) {
                        find = true;
                        flag = unit1.cosMap.get(poly1).equals(unit2.cosMap.get(poly2)) && flag;
                        break;
                    }
                }
                if (!find) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public Boolean sameUnit(Unit unit1, Unit unit2) {
        boolean flag = true;
        if (!(unit1.expX.equals(unit2.expX) && unit1.expY.equals(unit2.expY) &&
                unit1.expZ.equals(unit2.expZ) && unit1.sinMap.size() == unit2.sinMap.size() &&
                unit1.cosMap.size() == unit2.cosMap.size())) {
            flag = false;
        } else {    //建立在sinMap已经合并同类项的基础上
            for (Poly poly2 : unit2.sinMap.keySet()) {
                boolean find = false;
                for (Poly poly1 : unit1.sinMap.keySet()) {
                    if (poly2.equalsPoly(poly1, poly2)) {
                        find = true;
                        flag = unit1.sinMap.get(poly1).equals(unit2.sinMap.get(poly2)) && flag;
                        break;
                    }
                }
                if (!find) {
                    flag = false;
                }
            }
            for (Poly poly2 : unit2.cosMap.keySet()) {
                boolean find = false;
                for (Poly poly1 : unit1.cosMap.keySet()) {
                    if (poly2.equalsPoly(poly1, poly2)) {
                        find = true;
                        flag = unit1.cosMap.get(poly1).equals(unit2.cosMap.get(poly2)) && flag;
                        break;
                    }
                }
                if (!find) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!coe.equals(BigInteger.ZERO)) {
            if (!(coe.compareTo(BigInteger.ZERO) < 0)) {
                sb.append("+");
            }
            sb.append(coe);  //负数自己添符号？是的
            if (!expX.equals(BigInteger.ZERO)) {
                sb.append("*");
                if (expX.equals(BigInteger.ONE)) {
                    sb.append("x");
                } else {
                    sb.append("x**").append(expX);
                }
            }
            if (!expY.equals(BigInteger.ZERO)) {
                sb.append("*");
                if (expY.equals(BigInteger.ONE)) {
                    sb.append("y");
                } else {
                    sb.append("y**").append(expY);
                }
            }
            if (!expZ.equals(BigInteger.ZERO)) {
                sb.append("*");
                if (expZ.equals(BigInteger.ONE)) {
                    sb.append("z");
                } else {
                    sb.append("z**").append(expZ);
                }
            }
            for (Poly poly : sinMap.keySet()) {
                if (!(sinMap.get(poly).equals(BigInteger.ZERO))) {
                    sb.append("*sin((");
                    sb.append(poly.toString());
                    sb.append("))");
                    if (sinMap.get(poly).compareTo(BigInteger.ONE) > 0) {
                        sb.append("**").append(sinMap.get(poly).toString());
                    }
                }
            }
            for (Poly poly : cosMap.keySet()) {
                if (!(cosMap.get(poly).equals(BigInteger.ZERO))) {
                    sb.append("*cos((");
                    sb.append(poly.toString());
                    sb.append("))");
                    if (cosMap.get(poly).compareTo(BigInteger.ONE) > 0) {
                        sb.append("**").append(cosMap.get(poly).toString());
                    }
                }
            }
        } else {
            sb.append("+0");
        }
        return sb.toString();
    }

    public Poly derive(int flag) {
        //幂函数部分*三角函数求导
        Unit expUnit = new Unit();
        expUnit.coe = this.coe;
        expUnit.expX = this.expX;
        expUnit.expY = this.expY;
        expUnit.expZ = this.expZ;
        ArrayList<Unit> expUnitlist = new ArrayList<>();
        Poly expPoly = new Poly(expUnitlist);
        expUnitlist.add(expUnit);
        ArrayList<Unit> unitlist = new ArrayList<>();       //委屈求全的声明
        Poly poly = new Poly(unitlist);         //委屈求全的声明
        poly = poly.addPoly(this.csDerive(flag).mulPoly(expPoly));
        //三角函数*幂函数求导
        Unit csUnit = new Unit();
        csUnit.coe = BigInteger.ONE;
        csUnit.sinMap = this.sinMap;
        csUnit.cosMap = this.cosMap;
        ArrayList<Unit> csUnitlist = new ArrayList<>();
        Poly csPoly = new Poly(csUnitlist);
        csUnitlist.add(csUnit);
        poly = poly.addPoly(this.expDerive(flag).mulPoly(csPoly));
        return poly;
    }

    private Poly expDerive(int flag) {
        Unit unit = new Unit();
        switch (flag) {
            case 1:
                unit.coe = this.coe.multiply(expX);
                unit.expX = this.expX.equals(BigInteger.ZERO) ?
                        BigInteger.ZERO : this.expX.subtract(BigInteger.ONE);
                unit.expY = this.expY;
                unit.expZ = this.expZ;
                break;
            case 2:
                unit.coe = this.coe.multiply(expY);
                unit.expY = this.expY.equals(BigInteger.ZERO) ?
                        BigInteger.ZERO : this.expY.subtract(BigInteger.ONE);
                unit.expX = this.expX;
                unit.expZ = this.expZ;
                break;
            case 3:
                unit.coe = this.coe.multiply(expZ);
                unit.expZ = this.expZ.equals(BigInteger.ZERO) ?
                        BigInteger.ZERO : this.expZ.subtract(BigInteger.ONE);
                unit.expY = this.expY;
                unit.expX = this.expX;
                break;
            default:
        }
        ArrayList<Unit> unitlist = new ArrayList<>();
        unitlist.add(unit);
        return new Poly(unitlist);
    }

    private Poly sinDerive(int flag, Poly poly, BigInteger num) {
        Unit unit = new Unit();
        unit.coe = BigInteger.ONE;
        unit.cosMap.put(poly, BigInteger.ONE);
        ArrayList<Unit> unitlist = new ArrayList<>();
        if (!num.equals(BigInteger.ONE)) {
            unit.coe = unit.coe.multiply(num);
            unit.sinMap.put(poly, num.subtract(BigInteger.ONE));
        }
        unitlist.add(unit);
        Poly newpoly = new Poly(unitlist);
        return newpoly.mulPoly(poly.derive(flag));
    }

    private Poly cosDerive(int flag, Poly poly, BigInteger num) {
        Unit unit = new Unit();
        unit.coe = BigInteger.ONE.negate();
        unit.sinMap.put(poly, BigInteger.ONE);
        ArrayList<Unit> unitlist = new ArrayList<>();
        if (!num.equals(BigInteger.ONE)) {
            unit.coe = unit.coe.multiply(num);
            unit.cosMap.put(poly, num.subtract(BigInteger.ONE));
        }
        unitlist.add(unit);
        Poly newpoly = new Poly(unitlist);
        return newpoly.mulPoly(poly.derive(flag));
    }

    private Poly csDerive(int flag) {
        ArrayList<Unit> unitlist = new ArrayList<>();
        Poly poly = new Poly(unitlist);
        for (Poly poly1 : this.cosMap.keySet()) {
            Poly partPoly = cosDerive(flag, poly1, this.cosMap.get(poly1)); //一个求导乘上其他项
            for (Poly poly2 : this.cosMap.keySet()) {
                if (!poly1.equalsPoly(poly1, poly2)) {
                    Unit unit = new Unit();
                    unit.coe = BigInteger.ONE;
                    unit.cosMap.put(poly2, this.cosMap.get(poly2));
                    ArrayList<Unit> list = new ArrayList<>();
                    list.add(unit);
                    partPoly = partPoly.mulPoly(new Poly(list));
                }
            }
            for (Poly poly2 : this.sinMap.keySet()) {
                Unit unit = new Unit();
                unit.coe = BigInteger.ONE;
                unit.sinMap.put(poly2, this.sinMap.get(poly2));
                ArrayList<Unit> list = new ArrayList<>();
                list.add(unit);
                partPoly = partPoly.mulPoly(new Poly(list));
            }
            poly = poly.addPoly(partPoly);
        }
        for (Poly poly1 : this.sinMap.keySet()) {
            Poly partPoly = sinDerive(flag, poly1, this.sinMap.get(poly1));
            for (Poly poly2 : this.cosMap.keySet()) {
                Unit unit = new Unit();
                unit.coe = BigInteger.ONE;
                unit.cosMap.put(poly2, this.cosMap.get(poly2));
                ArrayList<Unit> list = new ArrayList<>();
                list.add(unit);
                partPoly = partPoly.mulPoly(new Poly(list));
            }
            for (Poly poly2 : this.sinMap.keySet()) {
                if (!poly1.equalsPoly(poly1, poly2)) {
                    Unit unit = new Unit();
                    unit.coe = BigInteger.ONE;
                    unit.sinMap.put(poly2, this.sinMap.get(poly2));
                    ArrayList<Unit> list = new ArrayList<>();
                    list.add(unit);
                    partPoly = partPoly.mulPoly(new Poly(list));
                }
            }
            poly = poly.addPoly(partPoly);
        }
        return poly;
    }
}

