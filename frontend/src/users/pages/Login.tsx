import { Text, VStack } from "@chakra-ui/react"
import { LoginForm } from "../components/LoginForm"

export const Login: React.FC = () => {
    return (
        <VStack
            justifyContent='center'
        >
            <Text fontSize='6xl'>
                Login
            </Text>
            <LoginForm />
        </VStack>
    )
}