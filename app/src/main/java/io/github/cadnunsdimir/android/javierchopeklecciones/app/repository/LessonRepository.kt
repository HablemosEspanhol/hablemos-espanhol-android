package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question

class LessonRepository {
    companion object {
        private val db = mapOf(
            1 to Lesson(
                id = 1,
                questions = listOf(
                    Question(1, "El sol es amarillo", "O sol é amarelo"),
                    Question(2, "Ella bebe agua", "Ela bebe água"),
                    Question(3, "Nosotros comemos pan", "Nós comemos pão"),
                    Question(4, "El agua es azul", "A água é azul"), // Adicional
                    Question(5, "Ella come pan", "Ela come pão")    // Adicional
                )
            ),
            2 to Lesson(
                id = 2,
                questions = listOf(
                    Question(1, "Yo tengo un perro", "Eu tenho um cachorro"),
                    Question(2, "Tú eres mi amigo", "Você é meu amigo"),
                    Question(3, "Él corre rápido", "Ele corre rápido"),
                    Question(4, "Nosotros leemos un libro", "Nós lemos um livro"),
                    Question(5, "Ellas hablan español", "Elas falam espanhol")
                )
            ),
            3 to Lesson(
                id = 3,
                questions = listOf(
                    Question(1, "Ella tiene un gato", "Ela tem um gato"),
                    Question(2, "Nosotros vamos al parque", "Nós vamos ao parque"),
                    Question(3, "El coche es rojo", "O carro é vermelho"),
                    Question(4, "Ellos miran la televisión", "Eles assistem televisão"),
                    Question(5, "Yo escribo una carta", "Eu escrevo uma carta")
                )
            ),
            4 to Lesson(
                id = 4,
                questions = listOf(
                    Question(1, "La casa es grande", "A casa é grande"),
                    Question(2, "El niño juega", "O menino brinca"),
                    Question(3, "Ella canta bien", "Ela canta bem"),
                    Question(4, "Nosotros estudiamos mucho", "Nós estudamos muito"),
                    Question(5, "Tú bebes leche", "Você bebe leite")
                )
            ),
            5 to Lesson(
                id = 5,
                questions = listOf(
                    Question(1, "El libro está en la mesa", "O livro está na mesa"),
                    Question(2, "La flor es hermosa", "A flor é linda"),
                    Question(3, "Yo necesito ayuda", "Eu preciso de ajuda"),
                    Question(4, "Ellos trabajan duro", "Eles trabalham duro"),
                    Question(5, "Ella duerme tarde", "Ela dorme tarde")
                )
            ),
            6 to Lesson(
                id = 6,
                questions = listOf(
                    Question(1, "Mi hermano es alto", "Meu irmão é alto"),
                    Question(2, "La cena está lista", "O jantar está pronto"),
                    Question(3, "Nosotros queremos café", "Nós queremos café"),
                    Question(4, "El perro ladra fuerte", "O cachorro late alto"),
                    Question(5, "Ella compra frutas", "Ela compra frutas")
                )
            ),
            7 to Lesson(
                id = 7,
                questions = listOf(
                    Question(1, "El tren es rápido", "O trem é rápido"),
                    Question(2, "Yo estoy feliz", "Eu estou feliz"),
                    Question(3, "Tú vives aquí", "Você vive aqui"),
                    Question(4, "Ella abre la puerta", "Ela abre a porta"),
                    Question(5, "Nosotros vemos una película", "Nós vemos um filme")
                )
            ),
            8 to Lesson(
                id = 8,
                questions = listOf(
                    Question(1, "El gato está durmiendo", "O gato está dormindo"),
                    Question(2, "Ellos viajan mañana", "Eles viajam amanhã"),
                    Question(3, "Ella cocina pasta", "Ela cozinha macarrão"),
                    Question(4, "Yo limpio mi cuarto", "Eu limpo meu quarto"),
                    Question(5, "La música es suave", "A música é suave")
                )
            ),
            9 to Lesson(
                id = 9,
                questions = listOf(
                    Question(1, "La escuela es grande", "A escola é grande"),
                    Question(2, "Nosotros bebemos jugo", "Nós bebemos suco"),
                    Question(3, "El pájaro vuela", "O pássaro voa"),
                    Question(4, "Ella escucha la radio", "Ela escuta o rádio"),
                    Question(5, "Yo tomo el autobús", "Eu pego o ônibus")
                )
            ),
            10 to Lesson(
                id = 10,
                questions = listOf(
                    Question(1, "El examen es difícil", "O exame é difícil"),
                    Question(2, "Ella tiene sueño", "Ela está com sono"),
                    Question(3, "Nosotros vivimos lejos", "Nós vivemos longe"),
                    Question(4, "Tú aprendes rápido", "Você aprende rápido"),
                    Question(5, "Ellos comen pizza", "Eles comem pizza")
                )
            )
        )

        fun getLesson(id: Int): Lesson? = db[id]
        fun getAllLessons(): List<Lesson> = db.values.toList()
    }
}