# Mini-Project: 3D Ray Tracer in Java

## 📌 Overview
This project is a miniature 3D rendering engine built in Java using **ray tracing**. The engine allows defining a 3D scene composed of geometric shapes and light sources and produces a rendered image using physical light simulation techniques.

The project was developed using **TDD (Test Driven Development)** principles and includes thorough testing using **JUnit**. All code was written in **IntelliJ IDEA** with clear modular architecture and object-oriented design.

---

## 🧱 Project Structure

- **Primitives** – Basic elements such as `Point`, `Vector`, `Color`, `Material`.
- **Geometries** – Interface and implementations of geometric shapes: `Sphere`, `Plane`, `Triangle`, `Cylinder`, `Polygon`, etc.
- **Lighting** – Classes for different light sources: `DirectionalLight`, `PointLight`, `SpotLight`, with beam narrowing and intensity controls.
- **Scene** – Represents the 3D scene including geometries and lights.
- **Renderer** – Core rendering engine: `RayTracerBase`, `Camera`, `ImageWriter`.
- **Camera** – Responsible for generating rays through pixels and managing image generation parameters.
- **Tests** – Extensive unit tests for each class and integration between modules.

---

## 🎨 Features

- **Ray Tracing** – Calculates pixel colors based on intersection of rays with scene objects.
- **Image Output** – Creates `.jpg` image files with rendered scenes.
- **GeoGebra** – Used in early prototyping to visualize geometries and scene structure.

---

## 🖼️ Image Quality Enhancements

### ✅ Anti-Aliasing
Reduces jagged edges by casting multiple rays per pixel and averaging the results.

- `Camera` updated with `antiAliasingLevel` parameter.
- Adds `constructRays` and `averageColor` methods.
- Edge transitions become smoother and more realistic.

### ✅ Adaptive Super Sampling
Improves performance by casting additional rays **only when needed** (i.e., when pixel corners differ in color).

- Significant reduction in rendering time with minimal image quality loss.
- Detects homogenous pixels to skip unnecessary computation.

---

## ⚡ Performance Optimizations

### ✅ Multi-Threading
Parallelizes pixel rendering using multiple threads.

- `PixelManager` handles pixel assignment per thread.
- `threadsCount` parameter allows user to control thread usage.
- Rendering time reduced significantly (e.g., from 54 minutes to 17 minutes for 100 rays per pixel).

---

## ✨ Bonus Features

- `getNormal()` implementation for bounded cylinders.
- Ray intersections with `Polygon` and `Cylinder`.
- Transformations: translation and rotation.
- Narrow-beam spot lighting.
- Rendering scenes with dozens of shapes.
- Improved shadow handling with maxDistance parameter in `FindIntersection`.

---

## 📚 Bibliography

- Course presentations (theory and lab)
- GeoGebra software for scene modeling
- Personal guidance and feedback from course staff

---

## 👥 Contributors

- Project developed using pair programming.
- Special thanks to Leah Chaim for explaining course material.
- Technical support by Eliezer – much appreciated!

