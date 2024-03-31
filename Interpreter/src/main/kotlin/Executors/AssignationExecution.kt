package Interpreter.Executors


import ASTN.Assignation
import Interpreter.Value
import Interpreter.VariableType
import token.DataType

class AssignationExecution: Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(ast: Assignation, variables: MutableMap<String, Value>): String {
        val varName = ast.assignation.getValue()
        val type = getValueType(ast.assignation.getType(), ast.assignation.getInitialPosition())
        val value = binaryOperator.evaluate(ast.value, variables)
        if (variables.containsKey(varName)){
            if (value.getType() == type){
                variables[varName] = value
                return ""
            }
            throw Exception("Variable name $varName dosent match with value type [${ast.assignation.getInitialPosition().first}, ${ast.assignation.getInitialPosition().second}]")
        }else{
            throw Exception("Variable name $varName not found [${ast.assignation.getInitialPosition().first}, ${ast.assignation.getInitialPosition().second}]")
        }
    }

    private fun getValueType(dataType: DataType, position : Pair<Int , Int>): VariableType{
        return when(dataType){
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Type Dosent Exist ${dataType} [${position.first}, ${position.second}]")
        }
    }
}