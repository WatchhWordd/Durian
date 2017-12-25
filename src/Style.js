import {StyleSheet} from 'react-native'

var Style = StyleSheet.create({
    rootContainer: {
        flex: 1
    },

    displayContainer: {
        flex: 2,
        backgroundColor: '#193441'
    },

    displayContainerText:{
        color: 'white',
        fontSize: 38,
        fontWeight: 'bold',
        textAlign: 'right',
        padding: 20
    },

    inputButtonHighlighted: {
        backgroundColor: '#193441'
    },

    inputContainer: {
        flex: 8,
        backgroundColor: '#3E606F'
    },

    inputButton: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        borderWidth: 0.5,
        borderColor: '#91AA9D'
    },

    inputButtonText: {
        fontSize: 22,
        fontWeight: 'bold',
        color: 'white'
    },

    inputRow: {
        flex: 1,
        flexDirection: 'row'
    },

    imagePosition:{
        width:193,
        height:110
    },

    GreetingPosition:{
        alignItems: 'center'
    },

    flatContainer: {
        flex: 1,
        paddingTop: 22
    },
    flatItem: {
        padding: 10,
        fontSize: 18,
        height: 44,
    },

});

export default Style;