Main

Опис
Main — це Java-програма, яка демонструє асинхронну обробку великих масивів чисел за допомогою багатопоточності. Програма розділяє масив випадкових чисел на частини, обробляє їх у фонових потоках та обчислює квадрати чисел у кожній частині. Використовується `ExecutorService` для управління потоками та `Future` для збору результатів.

Функціонал
- Генерація масиву випадкових чисел в заданому користувачем діапазоні.
- Розбиття масиву на частини, обробка кожної частини в окремому потоці.
- Асинхронне виконання завдань і збір результатів через `Future`.
- Використання `CopyOnWriteArraySet` для зберігання унікальних результатів.
- Підрахунок часу виконання програми.


Приклад використання
Програма генерує масив чисел, обчислює квадрати чисел у кожному сегменті масиву та виводить результати на екран у вигляді списку з десяти елементів, а також час виконання.

Запуск
1. Клонуйте репозиторій.
2. Скомпілюйте та запустіть клас `Main.java` в середовищі з підтримкою Java.

Приклад виводу
"
Squared results (first 10): [1327.39, 3139.06, 1059.40, 34.39, 2487.85, 3348.20, 165.19, 5210.61, 183.77, 4320.53]
Execution time (ms): 5
"