package Rules

import ASTN.AST
import Enforcers.Enforcer
import Enforcers.LineJumpOnMethodEnforcer

class MethodRule(private val ammountOfJumpLine : String ,override val enforcer: List<Enforcer> = listOf()) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        val enfocers : MutableList<Enforcer> = enforcer.toMutableList()
        if (property.containsKey(ammountOfJumpLine)){
            enfocers.add(LineJumpOnMethodEnforcer(property[ammountOfJumpLine] as Int))
        }
        return MethodRule(ammountOfJumpLine, enfocers)
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun genericLine(ast: AST): String {
        when (ast) {
            is ASTN.Method -> {
                val newLine = StringBuilder()
                newLine.append(ast.methodName.getValue())
                newLine.append("(")
                //Missing OPTree Rule
                newLine.append(")")
                return newLine.toString()
            }
            else -> {
                return ""
            }
        }
    }
}