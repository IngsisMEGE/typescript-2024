package Interpreter.Executors

import ASTN.AST
import ASTN.Method
import Interpreter.Value

class PrintExecutor: Executor<Method> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(ast: Method, variables: MutableMap<String, Value>): String {
        if(ast.methodName.value == "println"){
            return binaryOperator.evaluate(ast.value, variables).getValue().toString()
        }else{
            throw Exception("Method not found")
        }
    }
}