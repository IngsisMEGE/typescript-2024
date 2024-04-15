package executors

import astn.VarDeclaration
import astn.VarDeclarationAssignation
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.BinaryOperatorReader
import interpreter.executors.Executor
import interpreter.executors.utils.ValueTypeAdapter
import java.util.Optional

class DeclarationAssignationExecution : Executor<VarDeclarationAssignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: VarDeclarationAssignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.varDeclaration.assignation.getValue()
        val type = getValueType(ast.varDeclaration)
        val value = binaryOperator.evaluate(ast.value, variables)
        if (!variables.containsKey(varName)) {
            if (value.getType() == type) {
                variables[varName] = Value(type, Optional.of(value.getValue()), ast.varDeclaration.isMutable)
                return ""
            } else {
                throw Exception(
                    "Type Mismatch at Line ${ast.varDeclaration.type.getInitialPosition().second} between $type and ${value.getType()}",
                )
            }
        } else {
            throw Exception("Variable '$varName' already exists at Line ${ast.varDeclaration.assignation.getInitialPosition().second}")
        }
    }

    private fun getValueType(ast: VarDeclaration): VariableType {
        return ValueTypeAdapter.transformDataTypeToValueType(ast.type.getType(), ast.type.getInitialPosition())
    }
}
