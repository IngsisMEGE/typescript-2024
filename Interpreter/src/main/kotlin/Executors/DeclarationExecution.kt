package interpreter.executors

import astn.VarDeclaration
import interpreter.Value
import interpreter.VariableType
import token.DataType
import java.util.Optional

class DeclarationExecution : Executor<VarDeclaration> {
    override fun execute(
        ast: VarDeclaration,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.assignation.getValue()
        val type = getValueType(ast.type.getType())
        if (!variables.containsKey(varName)) {
            variables[varName] = Value(type, Optional.empty())
            return ""
        } else {
            throw Exception("Variable Already Exists")
        }
    }

    private fun getValueType(dataType: DataType): VariableType {
        return when (dataType) {
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Unexpected type")
        }
    }
}
