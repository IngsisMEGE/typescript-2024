package Rules

import ASTN.AST
import Enforcers.DoubleDotDeclarationEnforcer
import Enforcers.Enforcer

class VarDeclarationRules(override val enforcer: List<Enforcer> = listOf()) : Rules {
    override fun isTheRuleIncluded(property: Map<String , Any>): Rules {
        var enforcers : List<Enforcer> = listOf()
        if (property["DoubleDotSpaceInFront"] == true) {
            if (property["DoubleDotSpaceInBack"] == true){
                enforcers = enforcer.plus(DoubleDotDeclarationEnforcer(property["DoubleDotSpaceInFront"] as Int, property["DoubleDotSpaceInBack"] as Int))
            }
        }
        return VarDeclarationRules(enforcers)
    }

    override fun enforceRule(ast: AST, code: String): String {
        when (ast) {
            is ASTN.VarDeclaration ->{
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.assignation.getValue())
                 newLine.append(" :")
                newLine.append(ast.type.getValue())
                newLine.append(";")

                var line = newLine.toString()
                for (enforcer in enforcer) {
                    line = enforcer.enforceRule(ast, line)
                }
                return line
            }
            else -> {
                return code
            }
        }


    }
}