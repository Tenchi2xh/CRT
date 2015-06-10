/*
 * Copyright (C) 2015 Hamza Haiken <tenchi@team2xh.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.team2xh.crt.language.compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.team2xh.crt.language.parser.CRTBaseVisitor;
import net.team2xh.crt.language.parser.CRTLexer;
import net.team2xh.crt.language.parser.CRTParser;
import net.team2xh.crt.language.parser.CRTParser.*;
import net.team2xh.crt.raytracer.Background;
import net.team2xh.crt.raytracer.Camera;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.lights.Light;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Settings;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.entities.csg.Difference;
import net.team2xh.crt.raytracer.entities.csg.Intersection;
import net.team2xh.crt.raytracer.entities.csg.Union;
import net.team2xh.crt.raytracer.lights.ParallelLight;
import net.team2xh.crt.raytracer.lights.PointLight;
import net.team2xh.crt.raytracer.math.Vector3;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
final public class Compiler extends CRTBaseVisitor {

    private final Script script;
    private final Scope scope;
    private final String code;

    private final static String RGB  = "rgb";
    private final static String RGBA = "rgba";
    private final static String VEC3 = "vec3";

    private Compiler(String code) {
        this.code = code;
        script = new Script();
        scope = new Scope();
    }

    public static Script compile(String code) {

        CRTLexer lexer = new CRTLexer(new ANTLRInputStream(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CRTParser parser = new CRTParser(tokens);
        ParseTree tree = parser.script();

        Compiler compiler = new Compiler(code);
        compiler.visit(tree);

        return compiler.script;
    }

    @Override
    public Script visitScript(ScriptContext ctx) {
        boolean hasSettings = false;
        boolean hasScene = false;
        for (StatementContext s: ctx.statement()) {
            Object o = s.accept(this);
            Class c = o.getClass();

            if (hasScene)
                throw new CompilerException(ctx, code, "Scene block must at the end of the script");

            if (c == Variable.class) {
                scope.add((Variable) o);
            } else if (c == Settings.class) {
                hasSettings = true;
                script.setSettings((Settings) o);
            } else if (c == Scene.class) {
                hasScene = true;
                script.setScene((Scene) o);
            } else {
                throw new CompilerException(ctx, code,
                        "Top-level statements must be either an assignment or settings/scene block");
            }

        }

        if (!hasSettings)
            throw new CompilerException("Script must define settings block");
        if (!hasScene)
            throw new CompilerException("Script must define a scene");

        script.getSettings().setParent(script.getScene());

        System.out.println(scope.getVariables());
        System.out.println(script.getScene());

        return script;
    }

    @Override
    public Settings visitSettings(SettingsContext ctx) {

        Settings settings = script.getSettings();
        Scene scene = script.getScene();

        for (AttributeContext a: ctx.attribute()) {
            ExpressionContext expr = a.expression();
            Object value = resolve(expr);
            switch (a.IDENTIFIER().getText()) {
                case "title":
                    assertAttributeType(value, "title", expr, String.class);
                    System.out.println("Compiling " + value + "...");
                    settings.setTitle((String) value);
                    break;
                case "author":
                    assertAttributeType(value, "author", expr, String.class);
                    settings.setAuthor((String) value);
                    break;
                case "date":
                    assertAttributeType(value, "date", expr, String.class);
                    settings.setTitle((String) value);
                    break;
                case "notes":
                    assertAttributeType(value, "notes", expr, String.class);
                    settings.setAuthor((String) value);
                    break;
                case "gamma":
                    assertAttributeType(value, "gamma", expr, Double.class);
                    settings.setGamma((Double) value);
                    break;
                case "background":
                    assertAttributeType(value, "background", expr, Background.class);
                    scene.setBackground((Background) value);
                    break;
                case "camera":
                    assertAttributeType(value, "camera", expr, Camera.class);
                    scene.setCamera((Camera) value);
                    break;
                case "lights":
                    assertAttributeType(value, "lights", expr, LinkedList.class);
                    LinkedList<Object> lights = (LinkedList) value;
                    for (Object o : lights) {
                        if (!(o instanceof Light))
                            throw new CompilerException(ctx, code, "Attribute 'light' must be of type Light");
                        scene.addLight((Light) o);
                    }
                break;
            }
        }

        return settings;
    }

    private void assertAttributeType(Object object, String name, ParserRuleContext ctx, Class expected) {
        if (object == null) {
            throw new CompilerException(ctx, code, "Attribute '" + name + "' is required.");
        }
        if (object.getClass() != expected)
            throw new CompilerException(ctx, code, "Attribute '" + name + "' must be of type " + expected.getSimpleName());
    }

    @Override
    public Scene visitScene(SceneContext ctx) {
        Scene scene = script.getScene();
        for (ExpressionContext expr : ctx.expression()) {
            Object o = resolve(expr);
            Class c = o.getClass();
            if (c == Variable.class) {
                scope.add((Variable) o);
            } else if (o instanceof Entity) {
                scene.add((Entity) o);
            }
        }
        return scene;
    }

    @Override
    public Object visitObject(ObjectContext ctx) {
        String name = ctx.NAME().getText();

        Map<String, Object> attributes = new HashMap<>();

        for (AttributeContext a: ctx.attribute()) {
            ExpressionContext expr = a.expression();
            Object value = resolve(expr);
            String key = a.IDENTIFIER().getText();
            attributes.put(key, value);
        }

        Object o = null;
        switch (name) {
            case "Material": {
                Object color = attributes.get("color");
                assertAttributeType(color, "color", ctx, Pigment.class);
                o = new Material((Pigment) color);

                Object reflectivity = attributes.get("reflectivity");
                if (reflectivity != null) {
                    assertAttributeType(reflectivity, "reflectivity", ctx, Double.class);
                    // TODO: reflectivity setter
                    o = new Material((Pigment) color, (double) reflectivity);
                }

                break;
            }
            case "Camera": {
                Object position = attributes.get("position");
                Object pointing = attributes.get("pointing");
                Object fov = attributes.get("fov");
                assertAttributeType(position, "position", ctx, Vector3.class);
                assertAttributeType(pointing, "pointing", ctx, Vector3.class);
                assertAttributeType(fov, "fov", ctx, Double.class);
                o = new Camera((Vector3) position, (Vector3) pointing, (double) fov);
                break;
            }
            case "ParallelLight": {
                Object from = attributes.get("from");
                Object pointing = attributes.get("pointing");
                Object color = attributes.get("color");
                assertAttributeType(from, "from", ctx, Vector3.class);
                assertAttributeType(pointing, "pointing", ctx, Vector3.class);
                assertAttributeType(color, "color", ctx, Pigment.class);
                o = new ParallelLight((Vector3) from, (Vector3) pointing, (Pigment) color);
                break;
            }
            case "PointLight": {
                Object origin = attributes.get("origin");
                Object color = attributes.get("color");
                assertAttributeType(origin, "origin", ctx, Vector3.class);
                assertAttributeType(color, "color", ctx, Pigment.class);
                o = new PointLight((Vector3) origin, (Pigment) color);

                Object ambient = attributes.get("ambient");
                if (ambient != null) {
                    assertAttributeType(ambient, "ambient", ctx, Double.class);
                    ((PointLight) o).setAmbient((double) ambient);
                }
                Object falloff = attributes.get("falloff");
                if (ambient != null) {
                    assertAttributeType(falloff, "falloff", ctx, Double.class);
                    ((PointLight) o).setFalloff((double) falloff);
                }
                break;
            }
            case "Sphere": {
                Object center = attributes.get("center");
                Object radius = attributes.get("radius");
                Object material = attributes.get("material");
                assertAttributeType(center, "center", ctx, Vector3.class);
                assertAttributeType(radius, "radius", ctx, Double.class);
                assertAttributeType(material, "material", ctx, Material.class);
                o = new Sphere((Vector3) center, (double) radius, (Material) material);
                break;
            }
            case "Box": {
                Object cornerA = attributes.get("cornerA");
                Object cornerB = attributes.get("cornerB");
                Object material = attributes.get("material");
                assertAttributeType(cornerA, "cornerA", ctx, Vector3.class);
                assertAttributeType(cornerB, "cornerB", ctx, Vector3.class);
                assertAttributeType(material, "material", ctx, Material.class);
                o = new Box((Vector3) cornerA, (Vector3) cornerB, (Material) material);
                break;
            }
            case "Plane": {
                Object normal = attributes.get("normal");
                Object position = attributes.get("position");
                Object material = attributes.get("material");
                assertAttributeType(normal, "normal", ctx, Vector3.class);
                assertAttributeType(position, "position", ctx, Vector3.class);
                assertAttributeType(material, "material", ctx, Material.class);
                o = new Plane((Vector3) normal, (Vector3) position, (Material) material);
                break;
            }
            case "Background": {
                Object color = attributes.get("color");
                Object image = attributes.get("image");
                Object horizon = attributes.get("horizon");
                Object sky = attributes.get("sky");
                Object angle = attributes.get("angle");


                if (image != null) {
                    assertAttributeType(image, "image", ctx, String.class);
                    if (angle != null) {
                        assertAttributeType(angle, "angle", ctx, Double.class);
                        o = new Background((String) image, (double) angle);
                    }
                    else {
                        o = new Background((String) image);
                    }
                } else if (horizon != null && sky != null) {
                    assertAttributeType(horizon, "horizon", ctx, Pigment.class);
                    assertAttributeType(sky, "sky", ctx, Pigment.class);
                    o = new Background((Pigment) horizon, (Pigment) sky);
                } else {
                    assertAttributeType(color, "color", ctx, Pigment.class);
                    o = new Background((Pigment) color);
                }
                break;
            }
        }
        return o;
    }

    @Override
    public Variable visitAssignment(AssignmentContext ctx) {
        Object left = ctx.expression(0).accept(this);
        Object right = ctx.expression(1).accept(this);

        if (left.getClass() != Identifier.class) {
            throw new CompilerException(ctx, code,
                    "Left-hand side of assignment must be an identifier");
        }

        Identifier name = (Identifier) left;

        return new Variable(name, right);
    }

    @Override
    public Identifier visitIdentifierPrimary(IdentifierPrimaryContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public Integer visitIntegerLiteral(IntegerLiteralContext ctx) {
        return Integer.parseInt(ctx.getText());
    }

    @Override
    public Double visitFloatLiteral(FloatLiteralContext ctx) {
        return Double.parseDouble(ctx.getText());

    }

    @Override
    public String visitStringLiteral(StringLiteralContext ctx) {
        String str = ctx.getText();
        // Unescape characters
        // http://stackoverflow.com/a/7847310
        str = StringEscapeUtils.unescapeJava(str);
        return str.substring(1, str.length() - 1);
    }

    @Override
    public Boolean visitBooleanLiteral(BooleanLiteralContext ctx) {
        return Boolean.parseBoolean(ctx.getText());
    }

    @Override
    public List<Object> visitList(ListContext ctx) {
        return visitExpressionList(ctx.expressionList());
    }

    @Override
    public List<Object> visitExpressionList(ExpressionListContext ctx) {
        List<ExpressionContext> exprs = new LinkedList<>();
        if (ctx != null)
            exprs = ctx.expression();
        List<Object> result = new LinkedList<>();
        for (int i = 0; i < exprs.size(); ++i) {
            result.add(resolve(exprs.get(i)));
        }
        return result;
    }

    @Override
    public Object visitCall(CallContext ctx) {
        Object left = ctx.expression().accept(this);
        List<Object> arguments = visitExpressionList(ctx.expressionList());

        if (left.getClass() != Identifier.class)
            throw new CompilerException(ctx, code,
                    "'" + left + "' muts be an identifier");

        Identifier name = (Identifier) left;
        double[] args;
        switch (name.getName()) {
            case RGB:
                args = checkArguments(arguments, RGB, 3, ctx);
                return new Pigment(args[0], args[1], args[2]);
            case RGBA:
                args = checkArguments(arguments, RGBA, 4, ctx);
                return new Pigment(args[0], args[1], args[2], args[3]);
            case VEC3:
                args = checkArguments(arguments, VEC3, 3, ctx);
                return new Vector3(args[0], args[1], args[2]);
            default:
                // TODO: macro call
                return null;
        }
    }

    private double[] checkArguments(List<Object> arguments, String name, int n, ParserRuleContext ctx) {
        String ex = "'" + name + "' takes " + n + " float arguments";
        if (arguments.size() != n)
            throw new CompilerException(ctx, code, ex);

        double[] args = new double[n];
        for (int i = 0; i < arguments.size(); ++i) {
            if (arguments.get(i).getClass() != Double.class)
                throw new CompilerException(ctx, code, ex);
            args[i] = (Double) arguments.get(i);
        }

        return args;
    }

    @Override
    public Object visitListAccess(ListAccessContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));

        if (left.getClass() != LinkedList.class)
            throw new CompilerException(ctx, code, "'" + left + "' is not a list");

        if (right.getClass() != Integer.class)
            throw new CompilerException(ctx, code, "List index must be an integer");

        List<Object> list = (LinkedList) left;
        Integer index = (Integer) right;

        if (index >= list.size())
            throw new CompilerException(ctx, code, "List index out of range");

        return list.get(index);
    }

    @Override
    public Object visitUnarySign(UnarySignContext ctx) {
        Object operand = resolve(ctx.expression());
        String sign = ctx.getChild(0).getText();

        if (operand.getClass() == Double.class) {
            switch (sign) {
                case "+":
                    return ((Double) operand);
                case "-":
                    return -((Double) operand);
            }
        }

        if (operand.getClass() == Integer.class) {
            switch (sign) {
                case "+":
                    return ((Integer) operand);
                case "-":
                    return -((Integer) operand);
            }
        }

        throw new CompilerException(ctx, code,
                "Unsupported type for unary operator '" + sign + "': " + operand.getClass().getSimpleName());
    }

    @Override
    public Boolean visitUnaryNot(UnaryNotContext ctx) {
        Object operand = resolve(ctx.expression());

        if (operand.getClass() == Boolean.class) {
            return !((Boolean) operand);
        }

        throw new CompilerException(ctx, code,
                "Unsupported type for unary operator '!': " + operand.getClass().getSimpleName());
    }

    @Override
    public Object visitMultiplication(MultiplicationContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));
        String operator = ctx.getChild(1).getText();

        Class l = left.getClass();
        Class r = right.getClass();
        Class d = Double.class;
        Class i = Integer.class;
        Class s = String.class;

        // Integer multiplication
        if (l == i && r == i) {
            Integer x = (Integer) left, y = (Integer) right;
            switch (operator) {
                case "*":
                    return x * y;
                case "/":
                    return x / y;
                case "%":
                    return x % y;
            }
        }

        // Double multiplication
        if ((l == i || l == d) && (r == i || r == d)) {
            Double x = (l == i) ? ((Integer) left).doubleValue() : (Double) left;
            Double y = (r == i) ? ((Integer) right).doubleValue() : (Double) right;
            switch (operator) {
                case "*":
                    return x * y;
                case "/":
                    return x / y;
                case "%":
                    return x % y;
            }
        }

        // String repetition
        if (l == s && r == i || l == i && r == s) {
            if (operator.equals("*")) {
                if (l == s)
                    return StringUtils.repeat((String) left, (Integer) right);

                return StringUtils.repeat((String) right, (Integer) left);
            }
        }

        throw new CompilerException(ctx, code,
                "Unsupported types for binary operator '" + operator + "': " + l.getSimpleName() + ", " + r.getSimpleName());
    }

    @Override
    public Object visitAddition(AdditionContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));
        String operator = ctx.getChild(1).getText();

        Class l = left.getClass();
        Class r = right.getClass();
        Class d = Double.class;
        Class i = Integer.class;
        Class s = String.class;
        Class b = Boolean.class;

        // Integer addition
        if (l == i && r == i) {
            Integer x = (Integer) left, y = (Integer) right;
            switch (operator) {
                case "+":
                    return x + y;
                case "-":
                    return x - y;
            }
        }

        // Double addition
        if ((l == i || l == d) && (r == i || r == d)) {
            Double x = (l == i) ? ((Integer) left).doubleValue() : (Double) left;
            Double y = (r == i) ? ((Integer) right).doubleValue() : (Double) right;
            switch (operator) {
                case "+":
                    return x + y;
                case "-":
                    return x - y;
            }
        }

        // String concatenation
        if (l == s) {
            if (operator.equals("+")) {
                if (r == i)
                    return (String) left + (Integer) right;
                if (r == d)
                    return (String) left + (Double) right;
                if (r == b)
                    return (String) left + (Boolean) right;
                if (r == s)
                    return (String) left + (String) right;
            }
        }
        if (r == s) {
            if (operator.equals("+")) {
                if (l == i)
                    return (Integer) left + (String) right;
                if (l == d)
                    return (Double) left + (String) right;
                if (l == b)
                    return (Boolean) left + (String) right;
            }
        }

        // Entity CSG
        if (left instanceof Entity && right instanceof Entity) {
            Entity x = (Entity) left, y = (Entity) right;
            switch (operator) {
                case "+":
                    return new Union(x, y);
                case "-":
                    return new Difference(x, y);
                case "^":
                    return new Intersection(x, y);
            }
        }

        throw new CompilerException(ctx, code,
                "Unsupported types for binary operator '" + operator + "': " + l.getSimpleName() + ", " + r.getSimpleName());
    }

    @Override
    public Boolean visitComparison(ComparisonContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));
        String operator = ctx.getChild(1).getText();

        Class l = left.getClass();
        Class r = right.getClass();
        Class d = Double.class;
        Class i = Integer.class;

        // Integer comparison
        if (l == i && r == i) {
            Integer x = (Integer) left, y = (Integer) right;
            switch (operator) {
                case "<=":
                    return x <= y;
                case ">=":
                    return x >= y;
                case "<":
                    return x < y;
                case ">":
                    return x > y;
            }
        }

        // Double comparison
        if ((l == i || l == d) && (r == i || r == d)) {
            Double x = (l == i) ? ((Integer) left).doubleValue() : (Double) left;
            Double y = (r == i) ? ((Integer) right).doubleValue() : (Double) right;
            switch (operator) {
                case "<=":
                    return x <= y;
                case ">=":
                    return x >= y;
                case "<":
                    return x < y;
                case ">":
                    return x > y;
            }
        }

        // Other
        if (operator.equals("==")) {
            return left.equals(right);
        }
        if (operator.equals("!=")) {
            return !(left.equals(right));
        }

        throw new CompilerException(ctx, code,
                "Unsupported types for binary operator '" + operator + "': " + l.getSimpleName() + ", " + r.getSimpleName());
    }


    @Override
    public Boolean visitBinaryAnd(BinaryAndContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));
        String operator = ctx.getChild(1).getText();

        Class l = left.getClass();
        Class r = right.getClass();
        Class b = Boolean.class;

        if (l == b && r == b) {
            Boolean x = (Boolean) left, y = (Boolean) right;
            return x && y;
        }

        throw new CompilerException(ctx, code,
                "Unsupported types for binary operator '" + operator + "': " + l.getSimpleName() + ", " + r.getSimpleName());
    }

    @Override
    public Boolean visitBinaryOr(BinaryOrContext ctx) {
        Object left = resolve(ctx.expression(0));
        Object right = resolve(ctx.expression(1));
        String operator = ctx.getChild(1).getText();

        Class l = left.getClass();
        Class r = right.getClass();
        Class b = Boolean.class;

        if (l == b && r == b) {
            Boolean x = (Boolean) left, y = (Boolean) right;
            return x || y;
        }

        throw new CompilerException(ctx, code,
                "Unsupported types for binary operator '" + operator + "': " + l.getSimpleName() + ", " + r.getSimpleName());
    }

    @Override
    public Object visitTernary(TernaryContext ctx) {
        Object condition = resolve(ctx.expression(0));
        Object left = resolve(ctx.expression(1));
        Object right = resolve(ctx.expression(2));

        Class c = condition.getClass();
        Class l = left.getClass();
        Class r = right.getClass();
        Class b = Boolean.class;

        if (c != b)
            throw new CompilerException(ctx, code, "Ternary condition must be a Boolean");

        if (l == r) {
            return (Boolean) condition ? left : right;
        }

        throw new CompilerException(ctx, code,
                "Ternary results must be of the same type: " + l.getSimpleName() + ", " + r.getSimpleName());
    }


    @Override
    public Object visitPrimary(PrimaryContext ctx) {
        // ( expr )
        if (ctx.getChildCount() == 3)
            return ctx.getChild(1).accept(this);
        // literal or identifier
        return ctx.getChild(0).accept(this);
    }

    /**
     * Returns the value of an expression,
     * and if it is an identifier, fetches its value from the scope.
     *
     * @param expr Expression to resolve
     * @return Value of variable or evaluated expression
     */
    private Object resolve(ExpressionContext expr) {
        Object result = expr.accept(this);
        if (result.getClass() == Identifier.class) {
            result = scope.get((Identifier) result).getValue();
        }
        return result;
    }

}
