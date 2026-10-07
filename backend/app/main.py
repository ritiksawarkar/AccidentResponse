from fastapi import FastAPI

app = FastAPI(
    title="AccidentResponse API",
    description="Backend API for AccidentResponse vehicle safety system",
    version="1.0.0"
)


@app.get("/")
def root():
    return {
        "message": "AccidentResponse API is running"
    }


@app.get("/health")
def health_check():
    return {
        "status": "ok",
        "service": "AccidentResponse API"
    }