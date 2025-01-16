package j24.e01.f6_db_basics_exercise;

public record Product(
        Long id,
        String name,
        String description,
        Double price,
        Integer stockQuantity
) {}
